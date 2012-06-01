package example.android.dbsample;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.TimePicker;

public class DialogSampleActivity extends Activity {
    TextView label = null;
    ListView list = null;
    
    // onCreateメソッド
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialogsample);
        
        label = (TextView)findViewById(R.id.label_dialogtext);
//        list = (ListView)findViewById(R.id.listView1);
        
        Button dialogBtn = (Button)findViewById(R.id.dialogButton);   
        dialogBtn.setTag("dialog");
        dialogBtn.setOnClickListener(new ButtonClickListener()); 
        
        Button txtDialogBtn = (Button)findViewById(R.id.textDialogButton);
        txtDialogBtn.setTag("textDialog");
        txtDialogBtn.setOnClickListener(new ButtonClickListener());   
        
        Button SingleSelectDialogBtn = (Button)findViewById(R.id.singleSelectDialogButton);
        SingleSelectDialogBtn.setTag("singleSelectDialog");
        SingleSelectDialogBtn.setOnClickListener(new ButtonClickListener()); 
       
        Button DatePickerDialogBtn = (Button)findViewById(R.id.datePickerDialogButton);
        DatePickerDialogBtn.setTag("datePickerDialogBtn");
        DatePickerDialogBtn.setOnClickListener(new ButtonClickListener());
        
        Button TimePickerDialogBtn = (Button)findViewById(R.id.timePickerDialogButton);
        TimePickerDialogBtn.setTag("timePickerDialogBtn");
        TimePickerDialogBtn.setOnClickListener(new ButtonClickListener());
        
        Button ProgressDialogBtn = (Button)findViewById(R.id.progressDialogButton);
        ProgressDialogBtn.setTag("progressDialogBtn");
        ProgressDialogBtn.setOnClickListener(new ButtonClickListener());

        list = (ListView)findViewById(R.id.listView1);

    }
    
    class ButtonClickListener implements OnClickListener{
        public void onClick(View v) {
            String tag = (String)v.getTag();
            if (tag.equals("dialog")) {
                showDialog();
            } else if (tag.equals("textDialog")) {
                showTextDialog();
            } else if (tag.equals("singleSelectDialog")) {
                showSingleSelectDialog();
            } else if (tag.equals("datePickerDialogBtn")) {
                showDatePickerDialog();
            } else if (tag.equals("timePickerDialogBtn")) {
                showTimePickerDialog();
            } else if (tag.equals("progressDialogBtn")) {
                showProgressDialog();
            } 
        }
    }
    
    // 通常ダイアログの表示
    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(DialogSampleActivity.this);
        dialog.setTitle("通常ダイアログ");
        dialog.setMessage("選択してください。");
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
                label.setText("通常ダイアログ：OKが選択されました。");
            }
        });
        dialog.setNegativeButton("NG", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
                label.setText("通常ダイアログ：NGが選択されました。");
            }
        });
        dialog.show();
    }
    
    // テキストダイアログの表示
    public void showTextDialog() {  
        final EditText editText=new EditText(DialogSampleActivity.this);  
        AlertDialog.Builder dialog = new AlertDialog.Builder(DialogSampleActivity.this);
        dialog.setTitle("テキストダイアログ"); 
        dialog.setMessage("テキストを入力してください。");
        dialog.setView(editText);  
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
                label.setText("テキストダイアログ：" + editText.getText().toString() + "が入力されました。");
            }
        });  
        dialog.show();  
    }  
    
    // 単一選択ダイアログの表示
    final String[] items = new String[]{"もも","うめ","さくら"};
    int which = 0;
    public void showSingleSelectDialog() {      
        AlertDialog.Builder dialog = new AlertDialog.Builder(DialogSampleActivity.this);  
        dialog.setTitle("単一選択ダイアログ");  
        dialog.setSingleChoiceItems(items
               ,0
               ,new DialogInterface.OnClickListener() {        
                    public void onClick(DialogInterface dialog, int whichButton) {
                        which = whichButton;
                    }
                }
        );
        
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
                String selected = items[which];
                label.setText("単一選択ダイアログ：" + selected + "が入力されました。");
            }
        });  
        dialog.show(); 
    }
    
    // 日付選択ダイアログの表示
    public void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        
        DatePickerDialog dialog = new DatePickerDialog(DialogSampleActivity.this
                ,new DatePickerDialog.OnDateSetListener() { 
                    
                    public void onDateSet(DatePicker picker, int year, int month, int day) {
                        label.setText("日付選択ダイアログ：" + year + "年" + (month+1) + "月" + day + "日");
                    }
                }
                ,cal.get(Calendar.YEAR)
                ,cal.get(Calendar.MONTH)
                ,cal.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show(); 
    }
    
    // 時間選択ダイアログの表示
    public void showTimePickerDialog() {
        TimePickerDialog dialog = new TimePickerDialog(DialogSampleActivity.this
                ,new TimePickerDialog.OnTimeSetListener() { 
                    
                    public void onTimeSet(TimePicker picker, int hour, int min) {
                        label.setText("時間選択ダイアログ：" + hour + "時" + min + "分");
                    }
                }
                ,0
                ,0
                ,true
        );
        dialog.show();
    }
    
    // プログレスバーダイアログの表示
    ProgressDialog  dialog;
    public void showProgressDialog() {
        dialog = new ProgressDialog (DialogSampleActivity.this);
        dialog.setTitle("プログレスバーダイアログ");
        dialog.setMessage("お待ちください・・");
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);  
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {   
              public void onCancel(DialogInterface dialog) {
              }
        });
 
        // ProgressDialog の Cancel ボタン  
        dialog.setButton(  
          DialogInterface.BUTTON_NEGATIVE,  
          "Cancel",  
          new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialog, int which) {  
              // ProgressDialog をキャンセル  
              dialog.cancel();   
            }  
          }  
        );
        
        dialog.show(); 
        
        Thread th = new Thread(new ThreadClass());
        th.start();
        
        /*
        new Thread(new Runnable(){
            
            public void run(){
                try{
                    for(int i=0; i< dialog.getMax(); i++){
                        dialog.setProgress(i);
                        Thread.sleep(100);
                    }
                }catch(Exception e){
                    
                }
                dialog.dismiss();
            }
        }).start();     
        */
    }
    class ThreadClass implements Runnable{
        public void run(){
            try{
                for(int i=0; i< dialog.getMax(); i++){
                    dialog.setProgress(i);
                    Thread.sleep(100);
                }
            }catch(Exception e){
                
            }
            dialog.dismiss();
        }
        
    }
    
}
