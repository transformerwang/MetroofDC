package wyz.android.com.metroofdc.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

import dmax.dialog.SpotsDialog;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.listener.LoadDataListener;

/**
 * Created by wangyuzhe on 1/6/16.
 */
public class MetroInfoAsynctask extends AsyncTask<URL, Void, String[]> {

    private Context mContext;
    private LoadDataListener mLoadDataListener;
    private SpotsDialog spotsDialog;

    public MetroInfoAsynctask(Context context)
    {
        this.mContext = context;
    }

    public void setmLoadDataListener(LoadDataListener loadDataListener)
    {
        this.mLoadDataListener = loadDataListener;
    }

    @Override
    protected void onPreExecute() {
        spotsDialog = new SpotsDialog(mContext, R.style.Diloag);
        if(!((Activity)mContext).isFinishing())
        {
            spotsDialog.show();
        }
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(URL... params) {
        OkHttpClient client = new OkHttpClient();
        String[] body = new String[params.length];
        for(int i = 0; i< params.length; i++)
        {
            try {
                Request request = new Request.Builder().url(params[i]).build();
                Response response = client.newCall(request).execute();
                body[i] = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return body;
    }

    @Override
    protected void onPostExecute(String[] s) {
        if(!((Activity)mContext).isFinishing()) {
            spotsDialog.dismiss();
        }
//        Log.e("a", s[0]);
        if(mLoadDataListener != null)
        {
            if(s[0] != null && mContext != null) {
                mLoadDataListener.getData(s);
            }
            else
            {
                mLoadDataListener.getDataFail();
            }
        }
    }
}
