package com.znjt.xufeii.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;
import com.znjt.xufeii.R;
import com.znjt.xufeii.setting.IatSettings;
import com.znjt.xufeii.setting.TtsSettings;
import com.znjt.xufeii.sql.SQLiteData;
import com.znjt.xufeii.util.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class TtsDemo extends BaseActivity implements OnClickListener {
	private static String TAG = TtsDemo.class.getSimpleName(); 	
	// 语音合成对象
	private SpeechSynthesizer mTts;

	// 默认云端发音人
	public static String voicerCloud="xiaoyan";
	// 默认本地发音人
	public static String voicerLocal="xiaoyan";
		

	//缓冲进度
	private int mPercentForBuffering = 0;	
	//播放进度
	private int mPercentForPlaying = 0;

	private SQLiteData sqlData;
	private TextView mEt;
	private int item=0;
	private List<String> Words;
	private boolean isLoop=true;
	// 用HashMap存储听写结果
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
	// 语音听写对象
	private SpeechRecognizer mIat;
	private SharedPreferences mSharedPreferences;
	private boolean mTranslateEnable = false;
	private String mEngineType = "cloud";
	private Toast mToast;
	private RecognizerDialog mIatDialog;
	int ret = 0;// 函数调用返回值
	private boolean isLoop2=true;
	private boolean isLoop3=true;

	
	@SuppressLint("ShowToast")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ttsdemo);
		ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 321);
		initLayout();
		// 初始化合成对象
		initData();
		left();
		addActivity(this);
		mEt.setText("一共"+Words.size()+"条,当前为第0条");
		setText("听写");
		paly("可以开始了吗？");
	}

	private void initData() {
		Words=new ArrayList<>();
		mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);
		sqlData=new SQLiteData(this,"danci.db",null,1);
		mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, Activity.MODE_PRIVATE);
		mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
		String message="";
		SQLiteDatabase db=sqlData.getWritableDatabase();
		Cursor cursor=db.query("study",null,null,null,null,null,null);
		if (cursor.moveToFirst()){
			do {
				Words.add(cursor.getString(cursor.getColumnIndex("word")));
				if (message.equals("")){
					message+=cursor.getString(cursor.getColumnIndex("word"));
				}else {
					message+=","+cursor.getString(cursor.getColumnIndex("word"));
				}
			}while (cursor.moveToNext());
		}
		cursor.close();
		mEt.setText(message);
		// 初始化识别无UI识别对象
		// 使用SpeechRecognizer对象，可根据回调消息自定义界面；
		mIat = SpeechRecognizer.createRecognizer(this, mInitListener);

		// 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
		// 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
		mIatDialog = new RecognizerDialog(this,mInitListener);
		mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
	}

	/**
	 * 初始化Layout。
	 */
	private void initLayout() {
		findViewById(R.id.tts_play).setOnClickListener(this);
		
		findViewById(R.id.tts_cancel).setOnClickListener(this);
		findViewById(R.id.tts_pause).setOnClickListener(this);
		findViewById(R.id.tts_resume).setOnClickListener(this);
		//findViewById(R.id.image_tts_set).setOnClickListener(this);
		mEt=findViewById(R.id.tts_text);
	}	

	@Override
	public void onClick(View view) {
		if( null == mTts ){
			// 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
//			this.showTip( "创建对象失败，请确认 libmsc.so 放置正确，\n 且有调用 createUtility 进行初始化" );
			return;
		}
		
		switch(view.getId()) {
//		case R.id.image_tts_set:
//			Intent intent = new Intent(TtsDemo.this, TtsSettings.class);
//			startActivity(intent);
//			break;
		// 开始合成
		// 收到onCompleted 回调时，合成结束、生成合成音频
        // 合成的音频格式：只支持pcm格式
		case R.id.tts_play:
			startVideo();
			break;
		// 取消合成
		case R.id.tts_cancel:
			paly("已经取消此次听写");
			isLoop3=false;
			break;
		// 暂停播放
		case R.id.tts_pause:
			Intent inten5t=new Intent(TtsDemo.this,AllCheckActivity.class);
			startActivity(inten5t);
			break;
		// 继续播放
		case R.id.tts_resume:
			nextVideo();
			break;
		// 选择发音人
//		case R.id.tts_btn_person_select:
//			showPresonSelectDialog();
//			break;
		}
	}

	private void paly(String text) {
		// 设置参数
		setParam();
		int code = mTts.startSpeaking(text, mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
//			String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
//			int code = mTts.synthesizeToUri(text, path, mTtsListener);

		if (code != ErrorCode.SUCCESS) {
//			showTip("语音合成失败,错误码: " + code);
		}
	}



	private static int selectedNumCloud=0;
	private static int selectedNumLocal=0;
	/**
	 * 初始化监听。
	 */
	private InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			Log.d(TAG, "InitListener init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
//        		showTip("初始化失败,错误码："+code);
        	} else {
				// 初始化成功，之后可以调用startSpeaking方法
        		// 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
        		// 正确的做法是将onCreate中的startSpeaking调用移至这里
			}		
		}
	};

	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {
		
		@Override
		public void onSpeakBegin() {

		}

		@Override
		public void onSpeakPaused() {

		}

		@Override
		public void onSpeakResumed() {

		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
			// 合成进度
			mPercentForBuffering = percent;
//			showTip(String.format(getString(R.string.tts_toast_format),mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
			// 播放进度
			mPercentForPlaying = percent;
//			showTip(String.format(getString(R.string.tts_toast_format), mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onCompleted(SpeechError error) {
			Log.i("aaaaaaaaaaaa","eeeeeeeeee"+error+"---"+isLoop3);
			if (error == null) {
				Log.i("aaaaaaaaaaaa","eeeeeeeeee22222");
//				showTip("播放完成");
				Log.i("aaaaaaaaaaaa","eeeeeeeeee33333");
				if (isLoop3){
					start();
				}
			} else if (error != null) {
//				showTip(error.getPlainDescription(true));
			}
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
			//	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			//		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			//		Log.d(TAG, "session id =" + sid);
			//	}

			//实时音频流输出参考
			/*if (SpeechEvent.EVENT_TTS_BUFFER == eventType) {
				byte[] buf = obj.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER);
				Log.e("MscSpeechLog", "buf is =" + buf);
			}*/
		}
	};

//	private void showTip(final String str){
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				mToast.setText(str);
//				mToast.show();
//			}
//		});
//	}

	/**
	 * 参数设置
	 */
	private void setParam(){
		// 清空参数
		mTts.setParameter(SpeechConstant.PARAMS, null);
		//设置合成
		//设置使用云端引擎
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		//设置发音人
		mTts.setParameter(SpeechConstant.VOICE_NAME,voicerCloud);
		//mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY,"1");//支持实时音频流抛出，仅在synthesizeToUri条件下支持
		//设置合成语速
		mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
		//设置合成音调
		mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
		//设置合成音量
		mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "50"));
		//设置播放器音频流类型
		mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
		
		// 设置播放合成音频打断音乐播放，默认为true
		mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
		
		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
	}
	
	//获取发音人资源路径
	private String getResourcePath(){
		StringBuffer tempBuffer = new StringBuffer();
		//合成通用资源
		tempBuffer.append(ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "tts/common.jet"));
		tempBuffer.append(";");
		//发音人资源
		tempBuffer.append(ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "tts/"+TtsDemo.voicerLocal+".jet"));
		return tempBuffer.toString();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if( null != mTts ){
			mTts.stopSpeaking();
			// 退出时释放连接
			mTts.destroy();
		}
	}
	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
//				showTip("初始化失败，错误码：" + code);
			}
		}
	};
	private void start(){
		Log.i("aaaaaaaaaaaaaaaaa","start");
		mIatResults.clear();
		// 设置参数
		setParam2();
		boolean isShowDialog = mSharedPreferences.getBoolean(getString(R.string.pref_key_iat_show), false);
		if (isShowDialog) {
			Log.i("aaaaaaaaaaaaaaaaa","start11111");
			// 显示听写对话框
			mIatDialog.setListener(mRecognizerDialogListener);
			mIatDialog.show();
//			showTip(getString(R.string.text_begin));
		} else {
			Log.i("aaaaaaaaaaaaaaaaa","start22222");
			// 不显示听写对话框
			ret = mIat.startListening(mRecognizerListener);
			if (ret != ErrorCode.SUCCESS) {
//				showTip("听写失败,错误码：" + ret);
			} else {
//				showTip(getString(R.string.text_begin));
			}
		}
	}
	/**
	 * 听写监听器。
	 */
	private RecognizerListener mRecognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {

			Log.i("aaaaaaaaaaaaaaaaa","mRecognizerListener111111");
			// 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
//			showTip("开始说话");
		}

		@Override
		public void onError(SpeechError error) {
			Log.i("aaaaaaaaaaaaaaaaa","mRecognizerListener22222");
			// Tips：
			// 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
			if(mTranslateEnable && error.getErrorCode() == 14002) {
//				showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
			} else {
				if (isLoop2){
					isLoop2=false;
					paly("怎么样？是暂停还是继续？如果不方便听写也可以取消，下次再来？");
				}else {
					isLoop3=false;
					isLoop2=true;
					paly("你还在吗？听不到你的声音，本次听写到此结束吧，下次再来。");
				}
//				showTip(error.getPlainDescription(true));
			}
		}

		@Override
		public void onEndOfSpeech() {
			Log.i("aaaaaaaaaaaaaaaaa","mRecognizerListener3333");
			// 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
//			showTip("结束说话");
		}
		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			Log.i("aaaaaaaaaaaaaaaaa","mRecognizerListener4444");
			if( mTranslateEnable ){
				printTransResult( results );
			}else{
				String text = JsonParser.parseIatResult(results.getResultString());
				Log.i("vvvvvvvvvvvvvvv",text);
				switch (text){
					case "开始吧":
						startVideo();
						break;
					case "ok":
						startVideo();
						break;
					case "可以":
						startVideo();
						break;
					case "下一个":
						nextVideo();
						break;
					case "继续":
						nextVideo();
						break;
					case "再说一次":
						String text3 ="";
						text3= Words.get(item);
						paly(text3);
						break;
					case "继续吧":
						nextVideo();
						break;
					case "取消":
						paly("已经取消此次听写");
						isLoop3=false;
						break;
					case "取消吧":
						paly("已经取消此次听写");
						isLoop3=false;
						break;
					case "好的，检查吧":
						Intent intent=new Intent(TtsDemo.this,AllCheckActivity.class);
						startActivity(intent);
						break;
					case "检查":
						Log.i("eeeeeeeeeee","aaaaaaaaaaa");
						Intent intent2=new Intent(TtsDemo.this,AllCheckActivity.class);
						startActivity(intent2);
						break;
					case "不用了，一会儿再检查":
						break;

				}
			}

			if(isLast) {
				//TODO 最后的结果
			}
		}

		@Override
		public void onVolumeChanged(int volume, byte[] data) {
//			showTip("当前正在说话");
			Log.d(TAG, "返回音频数据："+data.length);
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
			//	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			//		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			//		Log.d(TAG, "session id =" + sid);
			//	}
		}
	};
	private void nextVideo(){
		String text2 ="";
		text2= Words.get(item);
		item++;
		mEt.setText("一共"+Words.size()+"条,当前为第"+item+"条");
		if (item==Words.size()){
			paly(text2+",听写完毕，交给我来检查，你先休息一下?");
			item=0;
		}else {
			paly(text2);
		}
	}
	private void startVideo(){
		String text1 ="";
		text1= Words.get(item);
		item++;
		mEt.setText("一共"+Words.size()+"条,当前为第"+item+"条");
		if (item==Words.size()){
			paly(text1+",听写完毕，交给我来检查，你先休息一下?");
			item=0;
		}else {
			paly(text1);
		}
	}
	/**
	 * 听写UI监听器
	 */
	private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
		public void onResult(RecognizerResult results, boolean isLast) {
			Log.d(TAG, "recognizer result：" + results.getResultString());

			if( mTranslateEnable ){
				printTransResult( results );
			}else{
				String text = JsonParser.parseIatResult(results.getResultString());
				Log.i("vvvvvvvvvvvvvv",text);
			}
		}


		/**
		 * 识别回调错误.
		 */
		public void onError(SpeechError error) {
			if(mTranslateEnable && error.getErrorCode() == 14002) {
//				showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
			} else {
//				showTip(error.getPlainDescription(true));
			}
		}

	};
	private void printTransResult (RecognizerResult results) {
		String trans  = JsonParser.parseTransResult(results.getResultString(),"dst");
		String oris = JsonParser.parseTransResult(results.getResultString(),"src");

		if( TextUtils.isEmpty(trans)||TextUtils.isEmpty(oris) ){
//			showTip( "解析结果失败，请确认是否已开通翻译功能。" );
		}else{

		}

	}
	public void setParam2() {
		// 清空参数
		mIat.setParameter(SpeechConstant.PARAMS, null);
		String lag = mSharedPreferences.getString("iat_language_preference", "mandarin");
		// 设置引擎
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
		// 设置返回结果格式
		mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

		this.mTranslateEnable = mSharedPreferences.getBoolean(this.getString(R.string.pref_key_translate), false);
		if (mEngineType.equals(SpeechConstant.TYPE_LOCAL)) {
			// 设置本地识别资源
			mIat.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath2());
		}
		if (mEngineType.equals(SpeechConstant.TYPE_CLOUD) && mTranslateEnable) {
			Log.i(TAG, "translate enable");
			mIat.setParameter(SpeechConstant.ASR_SCH, "1");
			mIat.setParameter(SpeechConstant.ADD_CAP, "translate");
			mIat.setParameter(SpeechConstant.TRS_SRC, "its");
		}
		//设置语言，目前离线听写仅支持中文
		if (lag.equals("en_us")) {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
			mIat.setParameter(SpeechConstant.ACCENT, null);


			if (mEngineType.equals(SpeechConstant.TYPE_CLOUD) && mTranslateEnable) {
				mIat.setParameter(SpeechConstant.ORI_LANG, "en");
				mIat.setParameter(SpeechConstant.TRANS_LANG, "cn");
			}
		} else {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// 设置语言区域
			mIat.setParameter(SpeechConstant.ACCENT, lag);

			if (mEngineType.equals(SpeechConstant.TYPE_CLOUD) && mTranslateEnable) {
				mIat.setParameter(SpeechConstant.ORI_LANG, "cn");
				mIat.setParameter(SpeechConstant.TRANS_LANG, "en");
			}
		}

		// 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
		mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "10000"));

		// 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
		mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

		// 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
		mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "0"));

		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
	}
	private String getResourcePath2(){
		StringBuffer tempBuffer = new StringBuffer();
		//识别通用资源
		tempBuffer.append(ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "asr/common.jet"));
		tempBuffer.append(";");
		tempBuffer.append(ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "asr/sms.jet"));
		//识别8k资源-使用8k的时候请解开注释
		return tempBuffer.toString();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mToast=null;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

	}
}
