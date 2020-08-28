package com.example.stayontouch.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.stayontouch.Entitie.User;
import com.example.stayontouch.R;

import java.util.Objects;

public class AddSubordinateDialog extends AppCompatDialogFragment {
//    private User user;
//
//    public AddSubordinateDialog(User user) {
//        this.user = user;
//    }
    View view;
    EditText id ;
    EditText password ;
    private AddSubordinateDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddSubordinateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.add_subordinate_dialog,null);
        builder.setView(view);


        id = view.findViewById(R.id.id);
        password = view.findViewById(R.id.password);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String _password = password.getText().toString();
                Long _id = Long.parseLong(String.valueOf(id.getText()));
                listener.addSubordinate(_id,_password);
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public interface AddSubordinateDialogListener{
        void addSubordinate(Long id, String password);
    }
}
