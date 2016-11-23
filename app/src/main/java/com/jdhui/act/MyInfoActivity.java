package com.jdhui.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.ApplicationConsts;
import com.jdhui.R;
import com.jdhui.bean.MyPhotoBean;
import com.jdhui.bean.ResultCodeContentBean;
import com.jdhui.bean.ResultMyInfoContentBean;
import com.jdhui.http.AsyncHttpClient;
import com.jdhui.http.AsyncHttpResponseHandler;
import com.jdhui.http.RequestParams;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;
import com.jdhui.view.SelectPhotoDialog;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * 我的信息
 */
public class MyInfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgBack;
    private RelativeLayout mLayoutPhoto;
    private ImageView mImgPhoto;
    private Button mBtnSave;
    private Bitmap newZoomImage;

    /**
     * 表示选择的是相机--1
     */
    private static int CAMERA_REQUEST_CODE = 1;
    /**
     * 表示选择的是相册--2
     */
    private static int GALLERY_REQUEST_CODE = 2;
    /**
     * 表示选择的是裁剪--3
     */
    private static int CROP_REQUEST_CODE = 3;

    /**
     * 图片保存SD卡位置
     */
    private final static String IMG_PATH = Environment.getExternalStorageDirectory() + "/Jdehui/imgs/";

    private ResultMyInfoContentBean bean;
    private String userInfoId = null;
    private TextView mTvName, mTvID;
    private EditText mEditAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_my_info);
        initView();
        mHandler = new MyHandler();
        mthread = new Thread(myRunnable);
        requestMyInfoData(userInfoId);
//        requestPhotoData();
    }

    private void initView() {
        userInfoId = getIntent().getStringExtra("userInfoId");
        mImgBack = (ImageView) findViewById(R.id.id_img_back);
        mLayoutPhoto = (RelativeLayout) findViewById(R.id.id_my_info_layout_photo);
        mImgPhoto = (ImageView) findViewById(R.id.id_my_info_img_user);
        mTvName = (TextView) findViewById(R.id.id_my_info_tv_name);
        mTvID = (TextView) findViewById(R.id.id_my_info_tv_idcard);
        mEditAdress = (EditText) findViewById(R.id.id_my_info_edit_adress);
        mBtnSave = (Button) findViewById(R.id.id_my_info_btn_save);
        mImgBack.setOnClickListener(this);
        mLayoutPhoto.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
    }

    /**
     * 请求数据
     */
    private void requestMyInfoData(String userInfoId) {
        HtmlRequest.getMyInfo(MyInfoActivity.this, userInfoId, new BaseRequester.OnRequestListener() {

            @Override
            public void onRequestFinished(BaseParams params) {
                if (params.result != null) {
                    bean = (ResultMyInfoContentBean) params.result;
                    setData(bean);
                } else {
                    Toast.makeText(MyInfoActivity.this,
                            "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void setData(ResultMyInfoContentBean bean) {
        if (bean != null) {
            String url = bean.getPictureServerURL();
            mTvName.setText(bean.getUserName());
            mTvID.setText(StringUtil.replaceSubStringID(bean.getIdNo()));
            mEditAdress.setText(bean.getAddress());
            if (!TextUtils.isEmpty(url)) {
                new ImageViewService().execute(url);
                // Toast.makeText(activity,"成功", 0).show();
            } else {
                mImgPhoto.setImageDrawable(getResources().getDrawable(R.drawable.user_photo));
            }
        } else {
            mImgPhoto.setImageDrawable(getResources().getDrawable(R.drawable.user_photo));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_img_back:
                finish();
                break;
            case R.id.id_my_info_layout_photo:
                selectPhoto();
                break;
            case R.id.id_my_info_btn_save:
                String address = mEditAdress.getText().toString();
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(MyInfoActivity.this, "请输入地址", Toast.LENGTH_LONG).show();
                } else {
                    requestData(address);
                }
                break;
        }
    }

    private void requestData(String address) {
        try {
            HtmlRequest.saveAddress(MyInfoActivity.this, DESUtil.decrypt(PreferenceUtil.getUserId()), address,
                    new BaseRequester.OnRequestListener() {

                        @Override
                        public void onRequestFinished(BaseParams params) {
                            if (params.result != null) {
                                ResultCodeContentBean bean = (ResultCodeContentBean) params.result;
                                if (Boolean.parseBoolean(bean.getFlag())) {
                                    Toast.makeText(MyInfoActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(MyInfoActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MyInfoActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectPhoto() {
        SelectPhotoDialog mDialog = new SelectPhotoDialog(this, new SelectPhotoDialog.OnSelectPhotoChanged() {
            @Override
            public void onAlbum() {//相册
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }

            @Override
            public void onCamera() {//相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }

            @Override
            public void onCancel() {
            }
        });
        mDialog.show();
    }

    // 根据用户选择，返回图片资源
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (data == null) {
                return;
            } else {
                Bundle extras = data.getExtras();
                Uri path = data.getData();
                if (extras != null) {
                    Bitmap bm = extras.getParcelable("data");
                    Uri uri = saveBitmap(bm);
                    startImageZoom(uri);
                }
            }
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            Uri uri;
            uri = data.getData();
            Uri fileUri = convertUri(uri);
            startImageZoom(fileUri);
        } else if (requestCode == CROP_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            if (extras == null) {
                return;
            }
            Bitmap bm = extras.getParcelable("data");
            newZoomImage = zoomImage(bm, 90, 90);
            sendImage(newZoomImage);
        }

    }


    private Uri saveBitmap(Bitmap bm) {
        File tmpDir = new File(IMG_PATH);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
        File img = new File(IMG_PATH + "Test.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 70, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Uri convertUri(Uri uri) {
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            Bitmap zoomImage = zoomImage(bitmap, 500, 500);
            is.close();
            return saveBitmap(zoomImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void startImageZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 110);
        intent.putExtra("outputY", 110);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    private void sendImage(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
//		String img1 = new String((bytes, Base64.DEFAULT));
        try {
            String id = DESUtil.decrypt(PreferenceUtil.getUserId());

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            // String img = GetImageStr(IMG_PATH+"abc.jpg");
            params.add("photo", img);
            params.add("name", "abc.jpg");
            params.add("id", id);
            String url = ApplicationConsts.URL_UPLOADPHOTO;
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers,
                                      String content) {
                    super.onSuccess(statusCode, headers, content);
                    // requestPhotoData();
                    // mypersonal_img.setImageBitmap(bm);
                    mthread = new Thread(myRunnable);
                    mthread.start();

                }

                @Override
                public void onFailure(Throwable error, String content) {
                    super.onFailure(error, content);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MyHandler mHandler;
    private Thread mthread;

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mImgPhoto.setImageBitmap(newZoomImage);
            } else {
            }
        }

    }

    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            Message msg = mHandler.obtainMessage();
            msg.what = 1;
            mHandler.sendMessage(msg);
        }
    };

    /**
     * 下载图片(用户头像 )
     */
    private void requestPhotoData() {
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HtmlRequest.getPhotoState(this, userId, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                MyPhotoBean data = (MyPhotoBean) params.result;
                if (data != null) {
                    String url = data.getPictureServerURL();
                    if (!TextUtils.isEmpty(url)) {
                        new ImageViewService().execute(url);
                        // Toast.makeText(activity,"成功", 0).show();
                    } else {
                        mImgPhoto.setImageDrawable(getResources()
                                .getDrawable(R.drawable.user_photo));
                    }
                } else {
                    mImgPhoto.setImageDrawable(getResources().getDrawable(
                            R.drawable.user_photo));
                }
            }
        });
    }

    /**
     * 获取网落图片资源
     *
     * @return
     */
    class ImageViewService extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap data = getImageBitmap(params[0]);
            return data;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            if (result != null) {
                mImgPhoto.setImageBitmap(result);
                saveBitmap(result);
            } else {
                mImgPhoto.setImageDrawable(getResources().getDrawable(
                        R.drawable.user_default));
            }
        }

    }

    private Bitmap getImageBitmap(String url) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
