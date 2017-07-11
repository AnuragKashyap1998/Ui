package anurag.ui;

/**
 * Created by anurag on 09-07-2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<Details> detailsArrayList;
    public MyAdapter(Context context, ArrayList<Details> detailsArrayList) {
        super(context,0);
        this.context=context;
        this.detailsArrayList=detailsArrayList;
    }

    @Override
    public int getCount() {
        return detailsArrayList.size();
    }

    static class ExpenseViewHolder
    {
        TextView title;
        TextView date;
        TextView time;
        public ExpenseViewHolder(TextView title,TextView date,TextView time)
        {
            this.title=title;
            this.date=date;
            this.time=time;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.list_item,null);
            TextView title=convertView.findViewById(R.id.title);
            TextView date=convertView.findViewById(R.id.date);
            TextView time=convertView.findViewById(R.id.time);
            ExpenseViewHolder expense=new ExpenseViewHolder(title,date,time);
            convertView.setTag(expense);
        }
        Details details;
        details=detailsArrayList.get(position);
        ExpenseViewHolder expense=(ExpenseViewHolder) convertView.getTag();
        expense.title.setText(details.title);
        expense.date.setText(details.date);
        expense.time.setText(details.time);
        return convertView;
    }
}
