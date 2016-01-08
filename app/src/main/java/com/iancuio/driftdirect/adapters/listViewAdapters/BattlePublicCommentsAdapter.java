package com.iancuio.driftdirect.adapters.listViewAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.BattleJudgeAndComments;
import com.iancuio.driftdirect.customObjects.person.Judge;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Run;
import com.iancuio.driftdirect.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BattlePublicCommentsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    static PopupWindow fadePopup;
    static View popupView;
    static PopupWindow popupWindow;
    private View mainView;
    private Judge judge;
    private boolean firstDriver;
    private List<BattleJudgeAndComments> battleJudgeAndCommentsList;


    public BattlePublicCommentsAdapter(Context context, View mainView, List<BattleJudgeAndComments> battleJudgeAndCommentsList, boolean firstDriver) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mainView = mainView;
        this.battleJudgeAndCommentsList = battleJudgeAndCommentsList;
        overrideBackPressed(mainView);
    }

    @Override
    public int getCount() {
        return battleJudgeAndCommentsList.size();
    }

    @Override
    public Object getItem(int i) {
        return battleJudgeAndCommentsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {

        View listItem = view;
        final BattlePublicCommentsViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new BattlePublicCommentsViewHolder();

                listItem = inflater.inflate(R.layout.listview_row_public_battle_comments, viewGroup, false);
                viewHolder.judgeNameTextView = (TextView) listItem.findViewById(R.id.textView_publicBattleCommentsListViewRow_judgeName);
                viewHolder.positiveCommentsListView = (ListView) listItem.findViewById(R.id.listView_publicBattleCommentsListViewRow_positiveComments);
                viewHolder.negativeCommentsListView = (ListView) listItem.findViewById(R.id.listView_publicBattleCommentsListViewRow_negativeComments);

            listItem.setTag(viewHolder);
        } else {
            viewHolder = (BattlePublicCommentsViewHolder) view.getTag();
        }
        judge = new Judge();
        judge.setType(battleJudgeAndCommentsList.get(i).getJudgeType());
        judge.setName(battleJudgeAndCommentsList.get(i).getJudgeName());

        viewHolder.judgeNameTextView.setText(battleJudgeAndCommentsList.get(i).getJudgeName());

        viewHolder.positiveCommentsListView.setAdapter(new RunJudgeViewCommentsAdapter(context, battleJudgeAndCommentsList.get(i).getPositiveComments(), judge, true));
        Utils.setListViewHeightBasedOnItems(viewHolder.positiveCommentsListView);

        viewHolder.positiveCommentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dimBackground();
                showPopup(mainView, true, position, battleJudgeAndCommentsList.get(i).getPositiveComments());
            }
        });

        viewHolder.negativeCommentsListView.setAdapter(new RunJudgeViewCommentsAdapter(context, battleJudgeAndCommentsList.get(i).getNegativeComments(), judge, false));
        Utils.setListViewHeightBasedOnItems(viewHolder.negativeCommentsListView);

        viewHolder.negativeCommentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dimBackground();
                showPopup(mainView, false, position, battleJudgeAndCommentsList.get(i).getNegativeComments());
            }
        });

        return listItem;
    }

    public void showPopup(final View anchorView, boolean positiveComments, int i, List<Comment> commentList) {

        popupView = inflater.inflate(R.layout.popup_judge_comments_full_view, null);

        int percentWidth = (int) ((context.getResources().getDisplayMetrics().widthPixels) * 0.80);

        popupWindow = new PopupWindow(popupView, percentWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView judgeTypeTextView = (TextView) popupView.findViewById(R.id.textView_popupJudgeCommentsFullViewLayout_judgeType);
        TextView judgeNameTextView = (TextView) popupView.findViewById(R.id.textView_popupJudgeCommentsFullViewLayout_judgeName);
        CircleImageView positiveCircleImageView = (CircleImageView) popupView.findViewById(R.id.imageView_popupJudgeCommentsFullViewLayout_positive);
        CircleImageView negativeCircleImageView = (CircleImageView) popupView.findViewById(R.id.imageView_popupJudgeCommentsFullViewLayout_negative);
        TextView commentTextView = (TextView) popupView.findViewById(R.id.imageView_popupJudgeCommentsFullViewLayout_comment);
        Button dismissButton = (Button) popupView.findViewById(R.id.button_popupJudgeCommentsFullViewLayout_dismissButton);


        if (positiveComments) {
            negativeCircleImageView.setVisibility(View.GONE);
        } else {
            positiveCircleImageView.setVisibility(View.GONE);
        }

        judgeNameTextView.setText(judge.getName());
        judgeTypeTextView.setText(judge.getType());
        commentTextView.setText(commentList.get(i).getComment());

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                fadePopup.dismiss();
            }
        });

        popupWindow.setBackgroundDrawable(null);

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }


    private PopupWindow dimBackground() {

        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.judge_comments_fade_popup,
                (ViewGroup) ((Activity)context).findViewById(R.id.fadePopup));
        fadePopup = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        fadePopup.showAtLocation(layout, Gravity.NO_GRAVITY, 0, 0);
        return fadePopup;
    }

    private void overrideBackPressed(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                boolean result = false;
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                            fadePopup.dismiss();
                            popupWindow = null;
                            result = true;
                        } else {
                            result = false;
                        }
                    }
                }
                return result;
            }
        });
    }

}

class BattlePublicCommentsViewHolder {
    TextView judgeNameTextView;
    ListView positiveCommentsListView;
    ListView negativeCommentsListView;
}
