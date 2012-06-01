package example.android.dbsample;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DBSampleActivity extends Activity {
	CreateProductHelper helper = null;
    SQLiteDatabase db = null;	
    
    static String message;
	int i;
	String put_word;
	String put_text;
	private static int TIMEOUT_MESSAGE = 1;
	private static int INTERVAL = 1;
	
	private TextView label = null;

    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.dbsample);

        // ���b�Z�[�W�\���p���x���̐ݒ�
        label = (TextView)findViewById(R.id.message);

        // �o�^�{�^���̃N���b�N���X�i�[�ݒ�
        Button insertBtn = (Button)findViewById(R.id.insert);
        insertBtn.setTag("insert");
        insertBtn.setOnClickListener(new ButtonClickListener());
        // �X�V�{�^���̃N���b�N���X�i�[�ݒ�
        Button updatetBtn = (Button)findViewById(R.id.update);
        updatetBtn.setTag("update");
        updatetBtn.setOnClickListener(new ButtonClickListener());
        // �폜�{�^���̃N���b�N���X�i�[�ݒ�
        Button delBtn = (Button)findViewById(R.id.delete);
        delBtn.setTag("delete");
        delBtn.setOnClickListener(new ButtonClickListener());
        // �\���{�^���̃N���b�N���X�i�[�ݒ�
        Button selectBtn = (Button)findViewById(R.id.select); 
        selectBtn.setTag("select");
        selectBtn.setOnClickListener(new ButtonClickListener());
      
        // DB�쐬
        helper = new CreateProductHelper(DBSampleActivity.this);
    }

    // �N���b�N���X�i�[��`
    class ButtonClickListener implements OnClickListener {
    	// onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
    	public void onClick(View v){
    		// �^�O�̎擾
    		String tag = (String)v.getTag();
    		
    		// ���b�Z�[�W�\���p
        	i = 0;
            message  = "";
        	put_word = "";
        	put_text = "";


            // ���͏��擾
			EditText productid = (EditText)findViewById(R.id.id);
            EditText name = (EditText)findViewById(R.id.name);
            EditText price = (EditText)findViewById(R.id.price);
            EditText unit = (EditText)findViewById(R.id.unit);
            
            // �e�[�u�����C�A�E�g�I�u�W�F�N�g�擾
            TableLayout tablelayout = (TableLayout)findViewById(R.id.list);

            // �e�[�u�����C�A�E�g�̃N���A
            tablelayout.removeAllViews();
            
    		
    		// �o�^�{�^���������ꂽ�ꍇ
    		if(tag.equals("insert")){
                
                // �f�[�^�o�^
                try{
                    // �Y��DB�I�u�W�F�N�g�擾
                    db = helper.getWritableDatabase();
                    
                    // �g�����U�N�V��������J�n
                    db.beginTransaction();
                    
                    // �o�^�f�[�^�ݒ�
                    ContentValues val = new ContentValues();
                    val.put("productid", productid.getText().toString());
                    val.put("name", name.getText().toString());
                    val.put("price", price.getText().toString());
                    val.put("unit", unit.getText().toString());
                    // �f�[�^�o�^
                    db.insert("product", null, val);
                    
                    // �R�~�b�g
                    db.setTransactionSuccessful();
                    
                	// ���b�Z�[�W�ݒ�
                    message = "�f�[�^��o�^���܂����I";

                }catch(Exception e){
                	message = "�f�[�^�o�^�Ɏ��s���܂����I";
                	Log.e("ERROR",e.toString());
                }finally{
                    // �g�����U�N�V��������I��
                    db.endTransaction();
                    
                    // DB�I�u�W�F�N�g�N���[�Y
                    db.close(); 
                }           
                
            // �X�V�{�^���������ꂽ�ꍇ
    		}else if(tag.endsWith("update")){    			
                
    			// �t�@�C���̃f�[�^�폜
    			try{
    	            // �Y��DB�I�u�W�F�N�g�擾
    	            db = helper.getWritableDatabase();

    	            // �X�V����
    				String condition = null;
    				if(!productid.getText().toString().equals("")){
    					condition = "productid = '" + productid.getText().toString() + "'";
                        // ���b�Z�[�W�ݒ�
                        message = "�f�[�^���X�V���܂����I";
    				}
    				
                    // �g�����U�N�V��������J�n
                    db.beginTransaction();
                    
                    // �X�V�f�[�^�ݒ�
                    ContentValues val = new ContentValues();
                    val.put("name", name.getText().toString());
                    val.put("price", price.getText().toString());
                    val.put("unit", unit.getText().toString());
                    // �f�[�^�X�V
                    db.update("product", val, condition, null);

                    // �R�~�b�g
                    db.setTransactionSuccessful();
                    
                }catch(Exception e){
                    // ���b�Z�[�W�ݒ�
                    message = "�f�[�^�X�V�Ɏ��s���܂����I";
                    Log.e("ERROR",e.toString());
                }finally{
                    // �g�����U�N�V��������I��
                    db.endTransaction();
                    
                    // DB�I�u�W�F�N�g�N���[�Y
                    db.close(); 
                }           
    		
        	// �폜�{�^���������ꂽ�ꍇ
    		}else if(tag.endsWith("delete")){   			
                
    			// �t�@�C���̃f�[�^�폜
    			try{
    	            // �Y��DB�I�u�W�F�N�g�擾
    	            db = helper.getWritableDatabase();

    	            // �폜����
    				String condition = null;
    				if(!productid.getText().toString().equals("")){
    					condition = "productid = '" + productid.getText().toString() + "'";
                        // ���b�Z�[�W�ݒ�
                        message = "�f�[�^���폜���܂����I";
    				}
    				
                    // �g�����U�N�V��������J�n
                    db.beginTransaction();
                    
                    // �f�[�^�폜
                    db.delete("product", condition, null);

                    // �R�~�b�g
                    db.setTransactionSuccessful();
                    
                }catch(Exception e){
                    // ���b�Z�[�W�ݒ�
                    message = "�f�[�^�폜�Ɏ��s���܂����I";
                    Log.e("ERROR",e.toString());
                }finally{
                    // �g�����U�N�V��������I��
                    db.endTransaction();
                    
                    // DB�I�u�W�F�N�g�N���[�Y
                    db.close(); 
                }           
            
            // �\���{�^���������ꂽ�ꍇ
    		}else if(tag.equals("select")){
                
                // �f�[�^�擾
                try{
                	// �Y��DB�I�u�W�F�N�g�擾
                    db = helper.getReadableDatabase();
                
                    // �񖼒�`
                    String columns[] = {"productid","name","price","unit"};
                    
                    // �f�[�^�擾
                    Cursor cursor = db.query("product", columns, null, null, null, null, "productid");
                    
                    // �e�[�u�����C�A�E�g�̕\���͈͂�ݒ�
                    tablelayout.setStretchAllColumns(false);
                    
                    // �e�[�u���ꗗ�̃w�b�_���ݒ�
                    TableRow headrow = new TableRow(DBSampleActivity.this);
                    TextView headtxt1 = new TextView(DBSampleActivity.this);
                    headtxt1.setText(R.string.id); 
                    headtxt1.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt1.setWidth(50);
                    TextView headtxt2 = new TextView(DBSampleActivity.this);
                    headtxt2.setText(R.string.name);
                    headtxt2.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt2.setWidth(100);
                    TextView headtxt3 = new TextView(DBSampleActivity.this);
                    headtxt3.setText(R.string.price);
                    headtxt3.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt3.setWidth(60);          
                    TextView headtxt4 = new TextView(DBSampleActivity.this);
                    headtxt4.setText(R.string.unit);
                    headtxt4.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt4.setWidth(60);          
                    headrow.addView(headtxt1);
                    headrow.addView(headtxt2);
                    headrow.addView(headtxt3);
                    headrow.addView(headtxt4);
                    tablelayout.addView(headrow);
                    
                    // �擾�����f�[�^���e�[�u�����ו��ɐݒ�
                    while(cursor.moveToNext()){                	
                        
                        TableRow row = new TableRow(DBSampleActivity.this);
                        TextView productidtxt = new TextView(DBSampleActivity.this);
                        productidtxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        productidtxt.setText(cursor.getString(0));
                        TextView nametxt = new TextView(DBSampleActivity.this);
                        nametxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        nametxt.setText(cursor.getString(1));
                        TextView pricetxt = new TextView(DBSampleActivity.this);
                        pricetxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        pricetxt.setText(cursor.getString(2));
                        TextView unittxt = new TextView(DBSampleActivity.this);
                        unittxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        unittxt.setText(cursor.getString(3));
                        row.addView(productidtxt);
                        row.addView(nametxt);
                        row.addView(pricetxt);
                        row.addView(unittxt);
                        tablelayout.addView(row);
                        
                        // ���b�Z�[�W�ݒ�
                        message = "�f�[�^���擾���܂����I";
                    }
             
                }catch(Exception e){
                	// ���b�Z�[�W�ݒ�
                    message = "�f�[�^�擾�Ɏ��s���܂����I";
                    Log.e("ERROR",e.toString());      
                }finally{
                    // DB�I�u�W�F�N�g�N���[�Y
                    db.close();                 	
                }  
    		}

    		// �n���h�����s
    		handler.sendEmptyMessage(TIMEOUT_MESSAGE);
    	}
    }


	// ��������ꕶ�����o�͂���n���h��
	private Handler handler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {

			// �������z��ɂP�������Z�b�g
			char data[] = message.toCharArray();

			// �z�񐔂��擾
			int arr_num = data.length;

			if (i < arr_num) {
				if (msg.what == TIMEOUT_MESSAGE) {
					put_word = String.valueOf(data[i]);
					put_text = put_text + put_word;
					label.setText(put_text);
					handler.sendEmptyMessageDelayed(TIMEOUT_MESSAGE,INTERVAL * 50);
					i++;
				} else {
					super.dispatchMessage(msg);
				}
			}
		}
	};
	
}