package com.example.farhan.todoappSqlLite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farhan on 10/21/2017.
 */

public class CustomArrayAdapter extends ArrayAdapter<DataSource> {

    private ArrayList<DataSource> dataSources;
    private Context context;
    EditText editText;
    private EditText dialogEt;
    private TextView textView;

    public CustomArrayAdapter(Context context, ArrayList<DataSource> dataSources, EditText editText) {
        super(context, 0, dataSources);
        this.context = context;
        this.dataSources = dataSources;
        this.editText = editText;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }
      final  DataSource ds = dataSources.get(position);

        textView = (TextView) convertView.findViewById(R.id.mTextView);
        Button btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);

        textView.setText(ds.getTxt());

        // Edit Btn Click Listener
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Dialog Box
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                // Get the layout inflater
                LayoutInflater inflater = LayoutInflater.from(getContext());

                View v = inflater.inflate(R.layout.dialog_view, null);
                dialogEt = (EditText) v.findViewById(R.id.etDialog);

                dialogEt.setText(textView.getText().toString());

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(v)
                        // Add action buttons
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                String updateTxt = dialogEt.getText().toString();

                                if (!updateTxt.isEmpty()){

                                DataSource dataSource = DataSource.findById(DataSource.class, ds.getId());
                                dataSource.setTxt(updateTxt);
                                dataSource.save();

                                // Getting all data from Class and stores in LIst to show.
                                List<DataSource> contactsDb = DataSource.listAll(DataSource.class);
                                dataSources.clear();
                                dataSources.addAll(contactsDb);

                                notifyDataSetChanged();
                                Toast.makeText(context, "Data Updated", Toast.LENGTH_SHORT).show();

                                }else {
                                    dialogEt.setError("This Field Can't Be Empty");
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                // Create the AlertDialog
                AlertDialog dialog = builder.create();

                dialog.show();

            }
        });

        // Delete Btn Click Listener
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataSource.deleteInTx(DataSource.class, dataSources.get(position));
                dataSources.remove(position);
                notifyDataSetChanged();

                Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }


}
