/*
 * Copyright (c) 2015 Jonas Kalderstam.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nononsenseapps.notepad.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.nononsenseapps.helpers.SyncHelper;
import com.nononsenseapps.helpers.SyncStatusMonitor;
import com.nononsenseapps.helpers.TimeFormatter;
import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.database.Task;
import com.nononsenseapps.notepad.database.TaskList;
import com.nononsenseapps.notepad.fragments.DialogPassword.PasswordConfirmedListener;
import com.nononsenseapps.notepad.interfaces.MenuStateController;
import com.nononsenseapps.ui.DateView;
import com.nononsenseapps.ui.NoteCheckBox;
import com.nononsenseapps.util.AsyncTaskHelper;
import com.nononsenseapps.util.SharedPreferencesHelper;
import com.nononsenseapps.utils.views.TitleNoteTextView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@EFragment
public class TaskListFragment extends Fragment
    implements OnSharedPreferenceChangeListener, SyncStatusMonitor.OnSyncStartStopListener {

  // Must be less than -1
  public static final String LIST_ALL_ID_PREF_KEY = "show_all_tasks_choice_id";
  public static final int LIST_ID_ALL = -2;
  public static final int LIST_ID_OVERDUE = -3;
  public static final int LIST_ID_TODAY = -4;
  public static final int LIST_ID_WEEK = -5;

  public static final String LIST_ID = "list_id";
  public static final int LOADER_TASKS = 1;
  public static final int LOADER_CURRENT_LIST = 0;
  private static final String TAG = "TaskListFragment";

  RecyclerView listView;

  @SystemService
  LayoutInflater layoutInflater;

  SimpleSectionsAdapter mAdapter;

  SyncStatusMonitor syncStatusReceiver = null;

  private long mListId = -1;

  private TaskListFragment.TaskListCallbacks mListener;

  private String mSortType = null;
  private int mRowCount = 3;

  private LoaderCallbacks<Cursor> mCallback = null;

  private ActionMode mMode;
  private SwipeRefreshLayout mSwipeRefreshLayout;
  private boolean mDeleteWasUndone = false;
  private ItemTouchHelper touchHelper;

  public TaskListFragment() {
    super();
  }

  public static TaskListFragment_ getInstance(final long listId) {
    TaskListFragment_ f = new TaskListFragment_();
    Bundle args = new Bundle();
    args.putLong(LIST_ID, listId);
    f.setArguments(args);
    return f;
  }

  public static String whereOverDue() {
    return Task.Columns.DUE + " BETWEEN " + Task.OVERDUE + " AND " + Task.TODAY_START;
  }

  public static String andWhereOverdue() {
    return " AND " + whereOverDue();
  }

  public static String whereToday() {
    return Task.Columns.DUE + " BETWEEN " + Task.TODAY_START + " AND " + Task.TODAY_PLUS(1);
  }

  public static String andWhereToday() {
    return " AND " + whereToday();
  }

  public static String whereWeek() {
    return Task.Columns.DUE + " BETWEEN " + Task.TODAY_START + " AND (" + Task.TODAY_PLUS(5) +
        " -1)";
  }

  public static String andWhereWeek() {
    return " AND " + whereWeek();
  }

  void loadList() {
    listView.setLayoutManager(new LinearLayoutManager(getActivity()));
    listView.setHasFixedSize(true);
    // TODO separators
    touchHelper = new ItemTouchHelper(new DragHandler());
    listView.setAdapter(mAdapter);
    touchHelper.attachToRecyclerView(listView);

    // TODO jonas
    /*listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
      final HashMap<Long, Task> tasks = new HashMap<Long, Task>();
      // ActionMode mMode;

      @Override
      public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Must setup the contextual action menu
        final MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.fragment_tasklist_context, menu);

        // Must clear for reuse
        tasks.clear();

        // For password
        mMode = mode;

        return true;
      }

      @Override
      public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // Here you can perform updates to the CAB due to
        // an invalidate() request
        return false;
      }

      @Override
      public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
        // Respond to clicks on the actions in the CAB
        boolean finish = false;
        int itemId = item.getItemId();
        if (itemId == R.id.menu_delete) {
          deleteTasks(tasks);
          finish = true;

        } else if (itemId == R.id.menu_switch_list) {
          // show move to list dialog
          DialogMoveToList.getInstance(tasks.keySet().toArray(new Long[tasks.size()]))
              .show(getFragmentManager(), "move_to_list_dialog");
          finish = true;
        } else if (itemId == R.id.menu_share) {
          startActivity(getShareIntent());
          finish = true;
        } else {
          finish = false;
        }

        if (finish) {
          mode.finish(); // Action picked, so close the CAB
        }
        return finish;
      }

      @Override
      public void onDestroyActionMode(ActionMode mode) {
        // Here you can make any necessary updates to the activity when
        // the CAB is removed. By default, selected items are
        // deselected/unchecked.
        tasks.clear();
      }

      @Override
      public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
                                            boolean checked) {
        if (checked) {
          tasks.put(id, new Task(mAdapter.getCursor(position)));
        } else {
          tasks.remove(id);
        }
        // Display in action bar total number of selected items
        mode.setTitle(Integer.toString(tasks.size()));
      }

      String getShareText() {
        final StringBuilder sb = new StringBuilder();
        final boolean locked = SharedPreferencesHelper.isPasswordSet(getActivity());
        for (Task t : tasks.values()) {
          if (sb.length() > 0) {
            sb.append("\n\n");
          }
          if (locked) {
            sb.append(t.title);
          } else {
            sb.append(t.getText());
          }
        }
        return sb.toString();
      }

      String getShareSubject() {
        String result = "";
        for (Task t : tasks.values()) {
          result += ", " + t.title;
        }
        return result.length() > 0 ? result.substring(2) : result;
      }

      Intent getShareIntent() {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getShareText());
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getShareSubject());
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        return shareIntent;
      }
    });*/
  }

  /**
   * Delete tasks and display a snackbar with an undo action
   *
   * @param taskMap
   */
  private void deleteTasks(final Map<Long, Task> taskMap) {
    final Task[] tasks = taskMap.values().toArray(new Task[taskMap.size()]);

    // If any are locked, ask for password first
    final boolean locked = SharedPreferencesHelper.isPasswordSet(getActivity());

    // Reset undo flag
    mDeleteWasUndone = false;

    // Dismiss callback
    final Snackbar.Callback dismissCallback = new Snackbar.Callback() {
      @Override
      public void onDismissed(Snackbar snackbar, int event) {
        // Do nothing if dismissed because action was pressed
        // Dismiss wil be called more than once if undo is pressed
        if (Snackbar.Callback.DISMISS_EVENT_ACTION != event && !mDeleteWasUndone) {
          // Delete them
          AsyncTaskHelper.background(new AsyncTaskHelper.Job() {
            @Override
            public void doInBackground() {
              for (Task t : tasks) {
                try {
                  t.delete(getActivity());
                } catch (Exception ignored) {
                }
              }
            }
          });
        }
      }
    };

    // Undo callback
    final View.OnClickListener undoListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mDeleteWasUndone = true;
        // Returns removed items to view
        // TODO jonas
        // mAdapter.reset();
      }
    };

    final PasswordConfirmedListener pListener = new PasswordConfirmedListener() {
      @Override
      public void onPasswordConfirmed() {
        removeTasksFromList(tasks);
        mListener.deleteTasksWithUndo(dismissCallback, undoListener, tasks);
      }
    };

    if (locked) {
      DialogPassword_ delpf = new DialogPassword_();
      delpf.setListener(pListener);
      delpf.show(getFragmentManager(), "multi_delete_verify");
    } else {
      // Just run it directly
      removeTasksFromList(tasks);
      mListener.deleteTasksWithUndo(dismissCallback, undoListener, tasks);
    }
  }

  private void removeTasksFromList(Task... tasks) {
    for (Task task : tasks) {
      // TODO jonas
      // mAdapter.remove(mAdapter.getItemPosition(task._id));
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    try {
      mListener = (TaskListFragment.TaskListCallbacks) getActivity();
    } catch (ClassCastException e) {
      throw new ClassCastException("Activity must implement " + "OnFragmentInteractionListener");
    }

    // We want to be notified of future changes to auto refresh
    PreferenceManager.getDefaultSharedPreferences(context)
        .registerOnSharedPreferenceChangeListener(this);
  }

  void setupSwipeToRefresh() {
    // Set the offset so it comes out of the correct place
    final int toolbarHeight = getResources().getDimensionPixelOffset(R.dimen.toolbar_height);
    mSwipeRefreshLayout
        .setProgressViewOffset(false, -toolbarHeight, Math.round(0.7f * toolbarHeight));

    // The arrow will cycle between these colors (in order)
    mSwipeRefreshLayout
        .setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2,
            R.color.refresh_progress_3);

    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        boolean syncing = SyncHelper.onManualSyncRequest(getActivity());

        if (!syncing) {
          // Do not show refresh view
          mSwipeRefreshLayout.setRefreshing(false);
        }
      }
    });
  }

  @Override
  public void onCreate(Bundle savedState) {
    super.onCreate(savedState);

    setHasOptionsMenu(true);

    syncStatusReceiver = new SyncStatusMonitor();

    if (getArguments().getLong(LIST_ID, -1) == -1) {
      throw new InvalidParameterException("Must designate a list to open!");
    }
    mListId = getArguments().getLong(LIST_ID, -1);

    // Start loading data
    mAdapter = new SimpleSectionsAdapter(getActivity());

    // Set a drag listener
    // TODO jonas
    /*mAdapter.setDropListener(new DropListener() {
      @Override
      public void drop(int from, int to) {
        Log.d("nononsenseapps drag", "Position from " + from + " to " + to);

        final Task fromTask = new Task((Cursor) mAdapter.getItem(from));
        final Task toTask = new Task((Cursor) mAdapter.getItem(to));

        fromTask.moveTo(getActivity().getContentResolver(), toTask);
      }
    });*/
  }

  /**
   * Called to have the fragment instantiate its user interface view. This is optional, and
   * non-graphical fragments can return null (which is the default implementation).  This will be
   * called between {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}. <p/> <p>If you
   * return a View from here, you will later be called in {@link #onDestroyView} when the view is
   * being released.
   *
   * @param inflater
   *     The LayoutInflater object that can be used to inflate any views in the fragment,
   * @param container
   *     If non-null, this is the parent view that the fragment's UI should be attached to.  The
   *     fragment should not add the view itself, but this can be used to generate the LayoutParams
   *     of the view.
   * @param savedInstanceState
   *     If non-null, this fragment is being re-constructed from a previous saved state as given
   *     here.
   * @return Return the View for the fragment's UI, or null.
   */
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    //return super.onCreateView(inflater, container, savedInstanceState);
    View rootView = inflater.inflate(R.layout.fragment_task_list, container, false);

    listView = (RecyclerView) rootView.findViewById(android.R.id.list);
//    listView = (RecyclerView) rootView.findViewById(R.id.notelist_recyclerview);
    loadList();
    // ListView will only support scrolling ToolBar off-screen from Lollipop onwards.
    // RecyclerView does not have this limitation
    ViewCompat.setNestedScrollingEnabled(listView, true);

    // setup swipe to refresh
    mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
    setupSwipeToRefresh();

    return rootView;
  }

  @Override
  public void onActivityCreated(final Bundle state) {
    super.onActivityCreated(state);

    // Get the global list settings
    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

    // Load pref for item height
    //mRowCount = prefs.getInt(getString(R.string.key_pref_item_max_height), 3);
    //mHideCheckbox = prefs.getBoolean(getString(R.string.pref_hidecheckboxes), false);

    // mSortType = prefs.getString(getString(R.string.pref_sorttype),
    // getString(R.string.default_sorttype));
    // mListType = prefs.getString(getString(R.string.pref_listtype),
    // getString(R.string.default_listtype));

    mCallback = new LoaderCallbacks<Cursor>() {
      @Override
      public Loader<Cursor> onCreateLoader(int id, Bundle arg1) {
        if (id == LOADER_CURRENT_LIST) {
          return new CursorLoader(getActivity(), TaskList.getUri(mListId), TaskList.Columns.FIELDS,
              null, null, null);
        } else {
          // What sorting to use
          Uri targetUri;
          String sortSpec;

          if (mSortType == null) {
            mSortType = prefs
                .getString(getString(R.string.pref_sorttype), getString(R.string.default_sorttype));
          }

          // All-view can't use manual sorting
          if (mListId < 1 && mSortType.equals(getString(R.string.const_possubsort))) {
            mSortType = getString(R.string.const_all_default_sorting);
          }

          if (mSortType.equals(getString(R.string.const_alphabetic))) {
            targetUri = Task.URI;
            sortSpec = getString(R.string.const_as_alphabetic, Task.Columns.TITLE);
          } else if (mSortType.equals(getString(R.string.const_duedate))) {
            targetUri = Task.URI_SECTIONED_BY_DATE;
            sortSpec = null;
          } else if (mSortType.equals(getString(R.string.const_modified))) {
            targetUri = Task.URI;
            sortSpec = Task.Columns.UPDATED + " DESC";
          }
          // manual sorting
          else {
            targetUri = Task.URI;
            sortSpec = Task.Columns.LEFT;
          }

          String where = null;
          String[] whereArgs = null;

          if (mListId > 0) {
            where = Task.Columns.DBLIST + " IS ?";
            whereArgs = new String[]{Long.toString(mListId)};
          }

          return new CursorLoader(getActivity(), targetUri, Task.Columns.FIELDS, where, whereArgs,
              sortSpec);
        }
      }

      @Override
      public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
        if (loader.getId() == LOADER_TASKS) {
          Log.d(TAG,
              "onLoadFinished() called");
          mAdapter.swapCursor(c);
        }
      }

      @Override
      public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == LOADER_TASKS) {
          mAdapter.swapCursor(null);
        }
      }
    };

    getLoaderManager().restartLoader(LOADER_TASKS, null, mCallback);
  }

  /**
   * Called when the fragment is visible to the user and actively running. This is generally tied to
   * {@link Activity#onResume() Activity.onResume} of the containing Activity's lifecycle.
   */
  @Override
  public void onResume() {
    super.onResume();
    // activate monitor
    if (syncStatusReceiver != null) {
      syncStatusReceiver.startMonitoring(getActivity(), this);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  /**
   * Called when the Fragment is no longer resumed.  This is generally tied to {@link
   * Activity#onPause() Activity.onPause} of the containing Activity's lifecycle.
   */
  @Override
  public void onPause() {
    //mSwipeRefreshLayout.setRefreshing(false);// deactivate monitor
    if (syncStatusReceiver != null) {
      syncStatusReceiver.stopMonitoring();
    }

    super.onPause();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    getLoaderManager().destroyLoader(0);
  }

  @Override
  public void onDetach() {
    mListener = null;
    PreferenceManager.getDefaultSharedPreferences(getActivity())
        .unregisterOnSharedPreferenceChangeListener(this);
    super.onDetach();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.fragment_tasklist, menu);
  }

  @Override
  public void onPrepareOptionsMenu(Menu menu) {
    if (getActivity() instanceof MenuStateController) {
      final boolean visible = ((MenuStateController) getActivity()).childItemsVisible();

      menu.setGroupVisible(R.id.list_menu_group, visible);
      if (!visible) {
        if (mMode != null) {
          mMode.finish();
        }
      }
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_clearcompleted:
        if (mListId != -1) {
          DialogDeleteCompletedTasks.showDialog(getFragmentManager(), mListId, null);
        }
        return true;
      default:
        return false;
    }
  }

  @Override
  public void onSharedPreferenceChanged(final SharedPreferences prefs, final String key) {
    if (isDetached()) {
      // Fix crash report
      return;
    }
    try {
      boolean reload = false;
      if (key.equals(getString(R.string.pref_sorttype))) {
        mSortType = null;
        reload = true;
      }
           /* else if (key.equals(getString(R.string.key_pref_item_max_height))) {
                mRowCount = prefs.getInt(key, 3);
                reload = true;
            } */
      /*else if (key.equals(getString(R.string.pref_hidecheckboxes))) {
        mHideCheckbox = prefs.getBoolean(key, false);
        reload = true;
      }*/

      if (reload && mCallback != null) {
        getLoaderManager().restartLoader(LOADER_TASKS, null, mCallback);
      }
    } catch (IllegalStateException ignored) {
      // Fix crash report
      // Might get a race condition where fragment is detached when getString is called
    }
  }

  /**
   * @param ongoing
   */
  @Override
  public void onSyncStartStop(boolean ongoing) {
    mSwipeRefreshLayout.setRefreshing(ongoing);
  }

  /**
   * This interface must be implemented by activities that contain TaskListFragments to allow an
   * interaction in this fragment to be communicated to the activity and potentially other fragments
   * contained in that activity.
   */
  public interface TaskListCallbacks {
    void openTask(final Uri uri, final long listId, final View origin);

    /**
     * Show a snackbar indicating that items were deleted, together with an undo button.
     */
    void deleteTasksWithUndo(Snackbar.Callback dismissCallback, View.OnClickListener listener,
                             Task... tasks);
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
      View.OnLongClickListener {

    private static final String TAG = "ViewHolder";
    final TitleNoteTextView title;
    final DateView date;
    final NoteCheckBox checkbox;
    final View dragHandle;
    final View dragPadding;
    long id = -1;

    public ViewHolder(View itemView) {
      super(itemView);

      title = (TitleNoteTextView) itemView.findViewById(android.R.id.text1);
      date = (DateView) itemView.findViewById(R.id.date);
      checkbox = (NoteCheckBox) itemView.findViewById(R.id.checkbox);
      dragHandle = itemView.findViewById(R.id.drag_handle);
      dragPadding = itemView.findViewById(R.id.dragpadding);

      itemView.setOnClickListener(this);
      itemView.setOnLongClickListener(this);

      dragHandle.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(final View v, final MotionEvent event) {
          Log.d(TAG, "onTouch() called with: " + "v = [" + v + "], event = [" + event + "]");
          if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "onTouch: starting Drag");
            touchHelper.startDrag(ViewHolder.this);
          }
          return false;
        }
      });
    }

    void onBind(final Cursor cursor) {
      id = cursor.getLong(0);
      //listId = cursor.getLong(cursor.getColumnIndex(Task.Columns.DBLIST));
      //this.callbacks = callbacks;
    }

    @Override
    public void onClick(final View v) {
      if (mListener != null && id > 0) {
        mListener.openTask(Task.getUri(id), mListId, v);
      }
    }

    @Override
    public boolean onLongClick(final View v) {
      // TODO
      //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
      // Also select the item in question
      //listView.setItemChecked(pos, true);
      return false;
    }
  }

  class SimpleSectionsAdapter extends RecyclerView.Adapter<ViewHolder> {
    final static int itemType = 0;
    final static int headerType = 1;
    private static final String TAG = "SimpleSectionsAdapter";
    final SharedPreferences prefs;
    final Context context;
    private final SimpleDateFormat weekdayFormatter;
    Cursor cursor = null;

    final OnCheckedChangeListener checkBoxListener = new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Task.setCompleted(context, isChecked, ((NoteCheckBox) buttonView).getNoteId());
      }
    };

    public SimpleSectionsAdapter(Context context) {
      super();
      setHasStableIds(true);
      this.context = context;
      prefs = PreferenceManager.getDefaultSharedPreferences(context);
      weekdayFormatter = TimeFormatter.getLocalFormatterWeekday(context);
    }

    int getViewLayout(final int viewType) {
      switch (viewType) {
        case headerType:
          return R.layout.tasklist_header;
        case itemType:
        default:
          return R.layout.tasklist_item_rich;
      }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
      final View view =
          LayoutInflater.from(context).inflate(getViewLayout(viewType), parent, false);

      ViewHolder viewHolder = new ViewHolder(view);
      return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
      if (!cursor.moveToPosition(position)) {
        return;
      }

      holder.onBind(cursor);

      if (headerType == getItemViewType(position)) {
        switch (cursor.getString(cursor.getColumnIndex(Task.Columns.TITLE))) {
          case Task.HEADER_KEY_OVERDUE:
            holder.title.setText(context.getString(R.string.date_header_overdue));
            break;
          case Task.HEADER_KEY_TODAY:
            holder.title.setText(context.getString(R.string.date_header_today));
            break;
          case Task.HEADER_KEY_PLUS1:
            holder.title.setText(context.getString(R.string.date_header_tomorrow));
            break;
          case Task.HEADER_KEY_PLUS2:
          case Task.HEADER_KEY_PLUS4:
          case Task.HEADER_KEY_PLUS3:
            holder.title.setText(weekdayFormatter.format(new Date(cursor.getLong(4))));
            break;
          case Task.HEADER_KEY_LATER:
            holder.title.setText(context.getString(R.string.date_header_future));
            break;
          case Task.HEADER_KEY_NODATE:
            holder.title.setText(context.getString(R.string.date_header_none));
            break;
          case Task.HEADER_KEY_COMPLETE:
            holder.title.setText(context.getString(R.string.date_header_completed));
            break;
        }
      } else {
        // Title
        holder.title.setMaxLines(mRowCount);
        holder.title
            .useSecondaryColor(!cursor.isNull(cursor.getColumnIndex(Task.Columns.COMPLETED)));
        holder.title.setTextTitle(cursor.getString(cursor.getColumnIndex(Task.Columns.TITLE)));

        // Note
        // Only if task it not locked
        // or only one line
        if (cursor.getInt(9) != 1 && mRowCount > 1) {
          holder.title.setTextRest(cursor.getString(cursor.getColumnIndex(Task.Columns.NOTE)));
        } else {
          holder.title.setTextRest("");
        }

        // Checkbox
        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setChecked(!cursor.isNull(cursor.getColumnIndex(Task.Columns.COMPLETED)));
        holder.checkbox.setNoteId(cursor.getLong(0));
        holder.checkbox.setOnCheckedChangeListener(checkBoxListener);
        //holder.checkbox.setVisibility(mHideCheckbox ? View.GONE : View.VISIBLE);

        // Due
        if (cursor.isNull(cursor.getColumnIndex(Task.Columns.DUE))) {
          holder.date.setVisibility(View.GONE);
        } else {
          holder.date.setVisibility(View.VISIBLE);
//          holder.date.setVisibility(View.INVISIBLE);
          holder.date.setTimeText(cursor.getLong(cursor.getColumnIndex(Task.Columns.DUE)));
        }

        if (mSortType != null && getString(R.string.const_possubsort).equals(mSortType)) {
          holder.dragHandle.setVisibility(View.VISIBLE);
          holder.dragPadding.setVisibility(View.VISIBLE);
        } else {
          holder.dragHandle.setVisibility(View.GONE);
          holder.dragPadding.setVisibility(View.GONE);
        }
      }
    }

    @Override
    public int getItemViewType(final int position) {
      cursor.moveToPosition(position);
      // If the id is invalid, it's a header
      if (cursor.getLong(0) < 1) {
        return headerType;
      } else {
        return itemType;
      }
    }

    @Override
    public int getItemCount() {
      if (cursor == null) {
        return 0;
      } else {
        return cursor.getCount();
      }
    }

    /*private int getItemPosition(final long id) {
      if (mCursor == null) {
        return -1;
      }
      mCursor.moveToPosition(-1);
      int i = 0;
      while (mCursor.moveToNext()) {
        if (id == mCursor.getLong(0)) {
          return i;
        }
        i += 1;
      }
      return -1;
    }*/

    public void swapCursor(final Cursor cursor) {
      this.cursor = cursor;
      notifyDataSetChanged();
    }

    public Cursor getCursor(final int position) {
      if (cursor != null) {
        cursor.moveToPosition(position);
      }
      return cursor;
    }
  }

  class DragHandler extends ItemTouchHelper.Callback {

    private static final String TAG = "DragHandler";

    public DragHandler() {
      super();
    }

    @Override
    public boolean isLongPressDragEnabled() {
      return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
      return false;
    }

    @Override
    public int getMovementFlags(final RecyclerView recyclerView,
                                final RecyclerView.ViewHolder viewHolder) {
      int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
      int swipeFlags = 0;
      return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder,
                          final RecyclerView.ViewHolder target) {
      final Task fromTask = new Task(mAdapter.getCursor(viewHolder.getAdapterPosition()));
      final Task toTask = new Task(mAdapter.getCursor(target.getAdapterPosition()));

      mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
      fromTask.moveTo(getActivity().getContentResolver(), toTask);
      return true;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, final int direction) {

    }
  }
}
