package com.evilgeniuses.hackathonyohack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniuses.hackathonyohack.R;
import com.evilgeniuses.hackathonyohack.models.FaqQuestion;

import java.util.List;

public class ExpandableFaqListAdapter extends RecyclerView.Adapter<ExpandableFaqListAdapter.QuestionViewHolder> {

    private List<FaqQuestion> faqQuestionList;
    private Context context;

    public ExpandableFaqListAdapter(Context context, List<FaqQuestion> faqQuestionList) {
        this.faqQuestionList = faqQuestionList;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_expandable_list_faq, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionViewHolder holder, final int position) {
        FaqQuestion faqQuestion = faqQuestionList.get(position);
        holder.textQuestion.setText(faqQuestion.getQuestion());
        holder.textAnswer.setText(faqQuestion.getAnswer());
    }

    @Override
    public int getItemCount() {
        return faqQuestionList.size();
    }


    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        public TextView textQuestion;
        public TextView textAnswer;
        public View expandableView;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            textQuestion = itemView.findViewById(R.id.textQuestion);
            expandableView = itemView.findViewById(R.id.expandableView);
            textAnswer = itemView.findViewById(R.id.textAnswer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableView.getVisibility() == View.GONE) {
                        expandableView.setVisibility(View.VISIBLE);
                    } else {
                        expandableView.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

}
