package com.iancuio.driftdirect.round.navigationView.top32_16.activities.judgeBattle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;

import java.util.List;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class RunJudgeSelectCommentsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<Comment> commentList;
    private boolean positive;


    public RunJudgeSelectCommentsAdapter(Context context, List<Comment> commentList, boolean positive) {
        this.context = context;
        this.commentList = commentList;
        inflater = LayoutInflater.from(context);
        this.positive = positive;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Comment getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        if (commentList.get(i).getId() != null) {
            return commentList.get(i).getId();
        } else {
            return 0;
        }
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        View listItem = view;
        final SelectCommentsViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new SelectCommentsViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_comment_selection, viewGroup, false);
            viewHolder.commentTextView = (TextView) listItem.findViewById(R.id.textView_commentSelectionListViewRow_comment);
            listItem.setTag(viewHolder);
        } else {
            viewHolder = (SelectCommentsViewHolder) view.getTag();
        }
        viewHolder.commentTextView.setText(commentList.get(i).getComment());

        if (positive) {
            if (((ListView) viewGroup).isItemChecked(i)) {
                viewHolder.commentTextView.setTextColor(context.getResources().getColor(R.color.top16Top32Winner));
            }
        } else {
            if (((ListView) viewGroup).isItemChecked(i)) {
                viewHolder.commentTextView.setTextColor(context.getResources().getColor(R.color.red));
            }
        }

        return listItem;
    }
}

class SelectCommentsViewHolder {
    TextView commentTextView;
}
