package example.android.dbsample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

public class CreateProductHelper  extends SQLiteOpenHelper{
    
    // コンストラクタ定義
    public CreateProductHelper(Context con){
        // SQLiteOpenHelperのコンストラクタ呼び出し
        super(con,"dbsample2",null,1);
    }
    
    // onCreateメソッド
    @Override
    public void onCreate(SQLiteDatabase db) {

        // テーブル作成
        try{
        	// SQL文定義
            String sql 
                 = "create table product(_id integer primary key autoincrement," +
                   "productid text not null," +
                   "name text not null," +
                   "price integer default 0," +
                   "unit integer default 0)";
            // SQL実行
            db.execSQL(sql);
            
            // メッセージ設定
            DBSampleActivity.message = "テーブルを作成しました！\n";
            
        }catch(Exception e){
        	DBSampleActivity.message  = "テーブルは作成されています！\n";
        	Log.e("ERROR",e.toString());
        }
    }
    
    // onUpgradeメソッド
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
    }
}
