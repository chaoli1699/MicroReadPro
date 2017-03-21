package cn.lenovo.microreadpro.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.baidu.tts.client.SpeechSynthesizeBag;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.utils.ParseDocument;
import cn.lenovo.microreadpro.utils.TTSpeaker;

/**
 * Created by Aaron on 2017/1/1.
 */

public class TTSpeakerService extends Service {

    private MyApplication mApp;
    private List<SpeechSynthesizeBag> bags;
    private int type; //0-html 1-url
    private String str;
    private Document doc;
    private int mount=0;
    private int domount=0;
    private String[] arr;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Bus.getDefault().register(this);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        type=intent.getIntExtra("type",0);
        str=intent.getStringExtra("str");

        if (type == 0) {
            doc=ParseDocument.parseHtmlFromString(str);
            setContent();
//            doSpeak();
        }else if (type == 1) {
            parseDocumentFromUrl.start();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Bus.getDefault().unregister(this);
        mApp.mTTSpeaker.stop();
    }

    private void doSpeak(){
        mApp.mTTSpeaker.batchSpeak(bags);
    }

    @BusReceiver
    public void onStringEvent(String event) {
        // handle your event
        if (event.equals("finish")){

            if (domount<=mount){
                speak100Item();
            }else if (domount>mount){
                speakOtherItem();
            }
        }
    }

    private void speak100Item(){
        bags.clear();
        for (int i=domount*100;i<(domount+1)*100;i++){
            bags.add(TTSpeaker.getSpeechSynthesizeBag(arr[i],i+""));
        }
        domount++;

        doSpeak();
    }

    private void speakOtherItem(){
        bags.clear();
        for (int j=domount*100;j<arr.length;j++){
            bags.add(TTSpeaker.getSpeechSynthesizeBag(arr[j],j+""));
        }
        domount++;

        doSpeak();
    }

    public void setContent() {

        String text = doc.text();
        arr=text.split("。");
        bags=new ArrayList<>();

        if (arr.length>100){

            mount=arr.length/100;
            speak100Item();
        }else {
//            bags=new ArrayList<>();
            for (int i=0;i<arr.length;i++){
                bags.add(TTSpeaker.getSpeechSynthesizeBag(arr[i],i+""));
            }
            doSpeak();
        }

    }

//    private void setContent2(){
//        Elements eles = doc.getElementsByTag("p");
//
//        if (eles.size()>0){
//            for (int i = 0; i < eles.size(); i++) {
//                String str = eles.get(i).text();
//                if (eles.size()<2){
//                    String[] arr=str.split("。");
//                    if (arr.length>0){
//                        for (int j=0;j<arr.length;j++){
//                            bags.add(TTSpeaker.getSpeechSynthesizeBag(arr[j],j+""));
//                        }
//                    }
//                }else {
//                    if (str.length() > 3) {
//                        if (!(str.substring(0, 3).equals("<img"))) {
//                            bags.add(TTSpeaker.getSpeechSynthesizeBag(str, i + ""));
//                        }
//                    }
//                }
//            }
//        }
//    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            doc= (Document) msg.obj;
            setContent();
        }
    };

    private Thread parseDocumentFromUrl=new Thread(new Runnable() {
        @Override
        public void run() {
            Document doc = null;
            try {
                doc = Jsoup.connect(str).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String title = doc.title();
            System.out.println(title);

            Message msg=new Message();
            msg.obj=doc;
            handler.sendMessage(msg);

        }
    });

}
