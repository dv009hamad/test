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

    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.dbsample);

        // メッセージ表示用ラベルの設定
        label = (TextView)findViewById(R.id.message);

        // 登録ボタンのクリックリスナー設定
        Button insertBtn = (Button)findViewById(R.id.insert);
        insertBtn.setTag("insert");
        insertBtn.setOnClickListener(new ButtonClickListener());
        // 更新ボタンのクリックリスナー設定
        Button updatetBtn = (Button)findViewById(R.id.update);
        updatetBtn.setTag("update");
        updatetBtn.setOnClickListener(new ButtonClickListener());
        // 削除ボタンのクリックリスナー設定
        Button delBtn = (Button)findViewById(R.id.delete);
        delBtn.setTag("delete");
        delBtn.setOnClickListener(new ButtonClickListener());
        // 表示ボタンのクリックリスナー設定
        Button selectBtn = (Button)findViewById(R.id.select); 
        selectBtn.setTag("select");
        selectBtn.setOnClickListener(new ButtonClickListener());
      
        // DB作成
        helper = new CreateProductHelper(DBSampleActivity.this);
    }

    // クリックリスナー定義
    class ButtonClickListener implements OnClickListener {
    	// onClickメソッド(ボタンクリック時イベントハンドラ)
    	public void onClick(View v){
    		// タグの取得
    		String tag = (String)v.getTag();
    		
    		// メッセージ表示用
        	i = 0;
            message  = "";
        	put_word = "";
        	put_text = "";


            // 入力情報取得
			EditText productid = (EditText)findViewById(R.id.id);
            EditText name = (EditText)findViewById(R.id.name);
            EditText price = (EditText)findViewById(R.id.price);
            EditText unit = (EditText)findViewById(R.id.unit);
            
            // テーブルレイアウトオブジェクト取得
            TableLayout tablelayout = (TableLayout)findViewById(R.id.list);

            // テーブルレイアウトのクリア
            tablelayout.removeAllViews();
            
    		
    		// 登録ボタンが押された場合
    		if(tag.equals("insert")){
                
                // データ登録
                try{
                    // 該当DBオブジェクト取得
                    db = helper.getWritableDatabase();
                    
                    // トランザクション制御開始
                    db.beginTransaction();
                    
                    // 登録データ設定
                    ContentValues val = new ContentValues();
                    val.put("productid", productid.getText().toString());
                    val.put("name", name.getText().toString());
                    val.put("price", price.getText().toString());
                    val.put("unit", unit.getText().toString());
                    // データ登録
                    db.insert("product", null, val);
                    
                    // コミット
                    db.setTransactionSuccessful();
                    
                	// メッセージ設定
                    message = "データを登録しました！";

                }catch(Exception e){
                	message = "データ登録に失敗しました！";
                	Log.e("ERROR",e.toString());
                }finally{
                    // トランザクション制御終了
                    db.endTransaction();
                    
                    // DBオブジェクトクローズ
                    db.close(); 
                }           
                
            // 更新ボタンが押された場合
    		}else if(tag.endsWith("update")){    			
                
    			// ファイルのデータ削除
    			try{
    	            // 該当DBオブジェクト取得
    	            db = helper.getWritableDatabase();

    	            // 更新条件
    				String condition = null;
    				if(!productid.getText().toString().equals("")){
    					condition = "productid = '" + productid.getText().toString() + "'";
                        // メッセージ設定
                        message = "データを更新しました！";
    				}
    				
                    // トランザクション制御開始
                    db.beginTransaction();
                    
                    // 更新データ設定
                    ContentValues val = new ContentValues();
                    val.put("name", name.getText().toString());
                    val.put("price", price.getText().toString());
                    val.put("unit", unit.getText().toString());
                    // データ更新
                    db.update("product", val, condition, null);

                    // コミット
                    db.setTransactionSuccessful();
                    
                }catch(Exception e){
                    // メッセージ設定
                    message = "データ更新に失敗しました！";
                    Log.e("ERROR",e.toString());
                }finally{
                    // トランザクション制御終了
                    db.endTransaction();
                    
                    // DBオブジェクトクローズ
                    db.close(); 
                }           
    		
        	// 削除ボタンが押された場合
    		}else if(tag.endsWith("delete")){   			
                
    			// ファイルのデータ削除
    			try{
    	            // 該当DBオブジェクト取得
    	            db = helper.getWritableDatabase();

    	            // 削除条件
    				String condition = null;
    				if(!productid.getText().toString().equals("")){
    					condition = "productid = '" + productid.getText().toString() + "'";
                        // メッセージ設定
                        message = "データを削除しました！";
    				}
    				
                    // トランザクション制御開始
                    db.beginTransaction();
                    
                    // データ削除
                    db.delete("product", condition, null);

                    // コミット
                    db.setTransactionSuccessful();
                    
                }catch(Exception e){
                    // メッセージ設定
                    message = "データ削除に失敗しました！";
                    Log.e("ERROR",e.toString());
                }finally{
                    // トランザクション制御終了
                    db.endTransaction();
                    
                    // DBオブジェクトクローズ
                    db.close(); 
                }           
            
            // 表示ボタンが押された場合
    		}else if(tag.equals("select")){
                
                // データ取得
                try{
                	// 該当DBオブジェクト取得
                    db = helper.getReadableDatabase();
                
                    // 列名定義
                    String columns[] = {"productid","name","price","unit"};
                    
                    // データ取得
                    Cursor cursor = db.query("product", columns, null, null, null, null, "productid");
                    
                    // テーブルレイアウトの表示範囲を設定
                    tablelayout.setStretchAllColumns(false);
                    
                    // テーブル一覧のヘッダ部設定
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
                    
                    // 取得したデータをテーブル明細部に設定
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
                        
                        // メッセージ設定
                        message = "データを取得しました！";
                    }
             
                }catch(Exception e){
                	// メッセージ設定
                    message = "データ取得に失敗しました！";
                    Log.e("ERROR",e.toString());      
                }finally{
                    // DBオブジェクトクローズ
                    db.close();                 	
                }  
    		}

    		// ハンドラ実行
    		handler.sendEmptyMessage(TIMEOUT_MESSAGE);
    	}
    }


	// 文字列を一文字ずつ出力するハンドラ
	private Handler handler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {

			// 文字列を配列に１文字ずつセット
			char data[] = message.toCharArray();

			// 配列数を取得
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