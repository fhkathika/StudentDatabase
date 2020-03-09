package com.example.studentdatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentlistAdapter extends ArrayAdapter<Student> {
    public  String Tag="StudentlistAdapter";
    Context context;

    private  List<Student> studentList;

    private  static  class ViewHolder{
        TextView id;
        TextView name;
        TextView mobile;
        TextView address;

    }

    public StudentlistAdapter(List<Student> myGuestArrayList, Context context) {

        super(context, R.layout.studentlistlayout, myGuestArrayList);

        this.studentList = studentList;
        this.context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Student guest=getItem(position);
        final ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.studentlistlayout, parent, false);
            viewHolder.id = convertView.findViewById(R.id.listid);
            viewHolder.name = convertView.findViewById(R.id.listname);
            viewHolder.mobile = convertView.findViewById(R.id.listmobile);
            viewHolder.address = convertView.findViewById(R.id.listaddress);

            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.id.setText(guest.getId());
        viewHolder.name.setText(guest.getName());
        viewHolder.mobile.setText(guest.getMobile());
        viewHolder.address.setText(guest.getAddress());
        // Return the completed view to render on screen
        return convertView;
    }
}
