package com.iancuio.driftdirect.adapters.listViewAdapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.person.Judge;
import com.iancuio.driftdirect.customObjects.person.PersonShort;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;
import com.iancuio.driftdirect.utils.RestUrls;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class RunJudgeViewCommentsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<Comment> commentList;
    private Judge judge;
    private boolean positive;



    public RunJudgeViewCommentsAdapter(Context context, List<Comment> commentList, Judge judge, boolean positive) {
        this.context = context;
        this.commentList = commentList;
        inflater = LayoutInflater.from(context);
        this.judge = judge;
        this.positive = positive;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {

        View listItem = view;
        final CommentsViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new CommentsViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_run_details_judge_comments, viewGroup, false);
            viewHolder.positiveCommentImageView = (CircleImageView) listItem.findViewById(R.id.imageView_commentListViewItem_positive);
            viewHolder.negativeCommentImageView = (CircleImageView) listItem.findViewById(R.id.imageView_commentListViewItem_negative);
            viewHolder.commentTextView = (TextView) listItem.findViewById(R.id.imageView_commentListViewItem_comment);
            listItem.setTag(viewHolder);
        } else {
            viewHolder = (CommentsViewHolder) view.getTag();
        }
            if (commentList.get(i).getPositive()) {
                viewHolder.negativeCommentImageView.setVisibility(View.GONE);
                viewHolder.commentTextView.setText(commentList.get(i).getComment());
            } else {
                viewHolder.positiveCommentImageView.setVisibility(View.GONE);
                viewHolder.commentTextView.setText(commentList.get(i).getComment());
            }


        return listItem;
    }


}

class CommentsViewHolder {
    CircleImageView positiveCommentImageView;
    CircleImageView negativeCommentImageView;
    TextView commentTextView;
}
