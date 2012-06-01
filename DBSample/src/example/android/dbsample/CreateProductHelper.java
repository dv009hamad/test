package example.android.dbsample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

public class CreateProductHelper  extends SQLiteOpenHelper{
    
    // �R���X�g���N�^��`
    public CreateProductHelper(Context con){
        // SQLiteOpenHelper�̃R���X�g���N�^�Ăяo��
        super(con,"dbsample2",null,1);
    }
    
    // onCreate���\�b�h
    @Override
    public void onCreate(SQLiteDatabase db) {

        // �e�[�u���쐬
        try{
        	// SQL����`
            String sql 
                 = "create table product(_id integer primary key autoincrement," +
                   "productid text not null," +
                   "name text not null," +
                   "price integer default 0," +
                   "unit integer default 0)";
            // SQL���s
            db.execSQL(sql);
            
            // ���b�Z�[�W�ݒ�
            DBSampleActivity.message = "�e�[�u�����쐬���܂����I\n";
            
        }catch(Exception e){
        	DBSampleActivity.message  = "�e�[�u���͍쐬����Ă��܂��I\n";
        	Log.e("ERROR",e.toString());
        }
    }
    
    // onUpgrade���\�b�h
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
    }
}
