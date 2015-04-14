package uk.co.dannybdutton.flashlightplus.fdialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import uk.co.dannybdutton.flashlightplus.R;


public class DialogInfoFragment extends DialogFragment {

    public DialogInfoFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title_warning)
                .setMessage(bundle.getString("Message"))
                .setPositiveButton(R.string.dialog_buttonText_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().dismiss();
                    }
                });
        return builder.create();
    }
}
