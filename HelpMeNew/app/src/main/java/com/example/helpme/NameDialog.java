package com.example.helpme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class NameDialog extends Dialog implements
        View.OnClickListener {

    public Activity c;
    TextView textView;
    public Dialog d;
    int img=0;
    ImageView g3,b3;
    Context context;

    public NameDialog(Activity a, Context c) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        context=c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.name_dialog_activity);
        g3=(ImageView)findViewById(R.id.g3);
        b3=(ImageView)findViewById(R.id.b3);
        textView=(TextView)findViewById(R.id.name) ;
        g3.setOnClickListener(this);
        b3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.g3:
                img=R.drawable.girlthree;
                break;
            case R.id.b3:
                img=R.drawable.boythree;
                break;
            default:
                break;
        }

        String string=textView.getText().toString();
        Intent in=new Intent(context,MainActivity.class);
        in.putExtra("id",img);
        in.putExtra("text",string);

        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in);
        c.finish();

        dismiss();
    }


}

