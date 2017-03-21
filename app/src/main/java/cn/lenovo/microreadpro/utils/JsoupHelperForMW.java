package cn.lenovo.microreadpro.utils;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.lenovo.microreadpro.model.ArticalBox;
import cn.lenovo.microreadpro.model.ArticalBox.Artical;

/**
 * Created by Aaron on 2017/3/2.
 */

public class JsoupHelperForMW {
//	//首栏-banner
//	Elements lm_box_w300_fl=doc.getElementsByClass("lm_box w300 fl");
//	Elements hdWrap_lm_box_fl=doc.getElementsByClass("hdWrap lm_box fl");
//	ArticalBox index_box=JsoupHelper.getIndexArticalBox(lm_box_w300_fl, hdWrap_lm_box_fl);
//	System.out.println(index_box);
//
//	//精美图文
//	Elements lm_box_lm_meitu=doc.getElementsByClass("lm_box lm_meitu");
//	ArticalBox meitu_box=JsoupHelper.getmeituBox(lm_box_lm_meitu);
//	System.out.println(meitu_box);
//
//	//美文类
//	Elements lm_box_w370_lm_index_fr=doc.getElementsByClass("lm_box w370 lm_index fr");
//	List<ArticalBox> meiwen_boxs=JsoupHelper.getNormalArticalBox(lm_box_w370_lm_index_fr);
//	System.out.println(meiwen_boxs);
//
//	//文章类
//	Elements lm_box_w370_lm_index_fl=doc.getElementsByClass("lm_box w370 lm_index fl");
//    List<ArticalBox> wenzhang_boxs=JsoupHelper.getNormalArticalBox(lm_box_w370_lm_index_fl);
//    System.out.println(wenzhang_boxs);

    public static ArticalBox getIndexArticalBox(Elements lm_box_w300_fl, Elements hdWrap_lm_box_fl){

        ArticalBox indexBox=new ArticalBox();
        List<Artical> index_box_arts=new ArrayList<ArticalBox.Artical>();

        List<Artical> baner_arts=getBanerArts(lm_box_w300_fl, "afocus");
        index_box_arts.addAll(baner_arts);

        //首栏-articals
        List<Artical> h2_arts=getH2Arts(hdWrap_lm_box_fl, "h2");
        index_box_arts.addAll(h2_arts);

        List<Artical> normal_arts=getNormalArts(hdWrap_lm_box_fl, "ttlist");
        index_box_arts.addAll(normal_arts);

        List<Artical> normal_arts2=getNormalArts(hdWrap_lm_box_fl, "nwlist");
        index_box_arts.addAll(normal_arts2);

        indexBox.setArticals(index_box_arts);

        return indexBox;
    }

    public static ArticalBox getMeituArticalBox(Elements lm_box_lm_meitu){

        ArticalBox meituBox=new ArticalBox();
        meituBox.setBox_title("精美图文");

        List<Artical> meitu_arts=getMeituArts(lm_box_lm_meitu, 0, "picList");
        meituBox.setArticals(meitu_arts);

        return meituBox;
    }

    public static List<ArticalBox> get2TypeArticalBox(Elements elements){

        List<ArticalBox> articalBoxs=new ArrayList<ArticalBox>();
        for(int j=0;j<elements.size();j++){
            Node strong=elements.get(j).childNode(1).childNode(1);
            String box_title=strong.childNode(0).childNode(0)+"";
            String box_path=strong.childNode(0).attr("href");

            ArticalBox yc_box=new ArticalBox();
            yc_box.setBox_title(box_title);
            yc_box.setBox_path(box_path);
            List<Artical> yc_box_arts=new ArrayList<ArticalBox.Artical>();

            List<Artical> pic_arts=getPicArts(elements, j, "picAtc pl");
            yc_box_arts.addAll(pic_arts);

            List<Artical> date_arts=getWithDateArts(elements, j, "atcList");
            yc_box_arts.addAll(date_arts);

            yc_box.setArticals(yc_box_arts);
            System.out.println(""+yc_box);
            articalBoxs.add(yc_box);
        }
        return articalBoxs;
    }

    public static List<ArticalBox> get3TypeArticalBox(Elements elements){

        List<ArticalBox> articalBoxs=new ArrayList<ArticalBox>();
        for(int j=0;j<elements.size();j++){
            Node strong=elements.get(j).childNode(1).childNode(1);
            String box_title=strong.childNode(0).childNode(0)+"";
            String box_path=strong.childNode(0).attr("href");

            ArticalBox yc_box=new ArticalBox();
            yc_box.setBox_title(box_title);
            yc_box.setBox_path(box_path);
            List<Artical> yc_box_arts=new ArrayList<ArticalBox.Artical>();

            List<Artical> pic_arts=getPicArts(elements, j, "w300 fl picAtc pl");
            yc_box_arts.addAll(pic_arts);

            List<Artical> date_arts=getWithDateArts(elements, j, "atcList fl Ml10");
            yc_box_arts.addAll(date_arts);

            List<Artical> meitu_arts=getMeituArts(elements, j, "picList w250 fr");
            yc_box_arts.addAll(meitu_arts);

            yc_box.setArticals(yc_box_arts);
            System.out.println(""+yc_box);
            articalBoxs.add(yc_box);
        }
        return articalBoxs;
    }

    public static ArticalBox getLatestArticalBox(Elements lm_box_lm_zjbang_w240_fr){

        ArticalBox latestBox=new ArticalBox();
        Node title_node=lm_box_lm_zjbang_w240_fr.get(0).childNode(1).childNode(1).childNode(0);
        latestBox.setBox_title(title_node+"");

        List<Artical> latest_arts=getLatestArts(lm_box_lm_zjbang_w240_fr);
        latestBox.setArticals(latest_arts);
        return latestBox;
    }

    public static ArticalBox getSimpleArticalBox(Elements elements){

        ArticalBox simpleBox=new ArticalBox();
        List<Artical> sim_arts=getSimpleArts(elements);
        simpleBox.setArticals(sim_arts);
        return simpleBox;
    }

    public static List<Artical> getBanerArts(Elements elements,String classLabel){

        List<Artical> baner_arts=new ArrayList<Artical>();
        Elements afocus=elements.get(0).getElementsByClass(classLabel);
        for(int i=0;i<afocus.get(0).childNode(1).childNodeSize()/2;i++){
            Artical art_afous=new Artical();
            Node afous_node= afocus.get(0).childNode(1).childNode(2*i+1).childNode(0);
            String title=afous_node.attr("title");
            String image_path=afous_node.attr("style").replace("background-image:url(", "").replace(")", "");
            String detail_path=afous_node.attr("href");

            art_afous.setTitle(title);
            art_afous.setImagePath(image_path);
            art_afous.setDetailPath(detail_path);
            art_afous.setOtherAttr("picArt");
            System.out.println(art_afous+"");
            baner_arts.add(art_afous);
        }
        return baner_arts;
    }

    public static List<Artical> getH2Arts(Elements elements,String classLable){

        List<Artical> h2_arts=new ArrayList<ArticalBox.Artical>();
        Elements h2=elements.get(0).getElementsByTag(classLable);
        for(int i=0;i<h2.size();i++){
            Node h2_node=h2.get(i).childNode(0);
            Artical art_h2=new Artical();
            String title=h2_node.childNode(0)+"";
            String detail_path=h2_node.attr("href");
            art_h2.setTitle(title);
            art_h2.setDetailPath(detail_path);
            art_h2.setOtherAttr("h2Size");
            System.out.println(""+art_h2);
            h2_arts.add(art_h2);
        }
        return h2_arts;
    }

    public static List<Artical> getNormalArts(Elements elements,String classLabel){
        List<Artical> normal_arts=new ArrayList<ArticalBox.Artical>();
        Elements nwlist=elements.get(0).getElementsByClass(classLabel);
        for(int i=0;i<nwlist.get(0).childNodeSize()/2;i++){
            Artical art_nwlist=new Artical();
            Node nwlist_node= nwlist.get(0).childNode(2*i+1);
            String title=nwlist_node.childNode(1).attr("title");
            String detail_path=nwlist_node.childNode(1).attr("href");

            String label=nwlist_node.childNode(0).childNode(1).childNode(0)+"";
            art_nwlist.setTitle(title);
            art_nwlist.setDetailPath(detail_path);
            art_nwlist.setLabel(label);
            art_nwlist.setOtherAttr("normal");
            System.out.println(art_nwlist+"");
            normal_arts.add(art_nwlist);
        }

        return normal_arts;
    }

    public static List<Artical> getMeituArts(Elements elements,int pos,String classLabel){

        List<Artical> meitu_arts=new ArrayList<ArticalBox.Artical>();
        Elements picList=elements.get(pos).getElementsByClass(classLabel);
        for(int i=0;i<picList.get(0).childNodeSize()/2;i++){
            Artical piclist_art=new Artical();
            Node piclist_node=picList.get(0).childNode(2*i+1).childNode(0);
            String title=piclist_node.childNode(0).attr("alt");
            String image_path=piclist_node.childNode(0).attr("data-original");
            String detail_path=piclist_node.attr("href");

            piclist_art.setTitle(title);
            piclist_art.setImagePath(image_path);
            piclist_art.setDetailPath(detail_path);
            piclist_art.setOtherAttr("meitu");
            System.out.println(""+piclist_art);
            meitu_arts.add(piclist_art);
        }
        return meitu_arts;
    }

    public static List<Artical> getWithDateArts(Elements elements ,int pos,String classLabel){

        List<Artical> date_arts=new ArrayList<ArticalBox.Artical>();
        Elements yc_atcList_fr=elements.get(pos).getElementsByClass(classLabel);
        for(int i=0;i<yc_atcList_fr.get(0).childNodeSize()/2;i++){
            Artical yc_atcList_fr_art=new Artical();
            Node yc_atcList_fr_node=yc_atcList_fr.get(0).childNode(2*i+1);
            String title=yc_atcList_fr_node.childNode(0).attr("title");
            String detail_path=yc_atcList_fr_node.childNode(0).attr("href");

            String publish_date=yc_atcList_fr_node.childNode(1).childNode(0)+"";
            yc_atcList_fr_art.setTitle(title);
            yc_atcList_fr_art.setDetailPath(detail_path);
            yc_atcList_fr_art.setPublishDate(publish_date);
            yc_atcList_fr_art.setOtherAttr("withDate");
            System.out.println(""+yc_atcList_fr_art);
            date_arts.add(yc_atcList_fr_art);
        }
        return date_arts;
    }

    public static List<Artical> getPicArts(Elements elements,int pos,String classLabel){

        List<Artical> pic_arts=new ArrayList<ArticalBox.Artical>();
        Elements yc_picAtc_pl_fr=elements.get(pos).getElementsByClass(classLabel);
        for(int i=0;i<yc_picAtc_pl_fr.get(0).childNodeSize()/2;i++){
            Artical yc_picAtc_pl_fr_art=new Artical();
            Node yc_picAtc_pl_fr_node=yc_picAtc_pl_fr.get(0).childNode(2*i+1).childNode(0);
            String title=yc_picAtc_pl_fr_node.childNode(0).attr("alt");
            String image_path=yc_picAtc_pl_fr_node.childNode(0).attr("data-original");
            String detail_path=yc_picAtc_pl_fr_node.attr("href");

            String short_desc=yc_picAtc_pl_fr.get(0).childNode(1).childNode(1).childNode(1).childNode(0)+"";
            yc_picAtc_pl_fr_art.setTitle(title);
            yc_picAtc_pl_fr_art.setImagePath(image_path);
            yc_picAtc_pl_fr_art.setDetailPath(detail_path);
            yc_picAtc_pl_fr_art.setShortDesc(short_desc);
            yc_picAtc_pl_fr_art.setOtherAttr("picArt");
            System.out.println(""+yc_picAtc_pl_fr_art);
            pic_arts.add(yc_picAtc_pl_fr_art);
        }

//        if (pic_arts.size()>1){
//            for (Artical art:pic_arts){
//                art.setOtherAttr("baner");
//                pic_arts.add(art);
//            }
//        }
        return pic_arts;
    }

    public static List<Artical> getLatestArts(Elements elements){

        List<Artical> latest_arts=new ArrayList<ArticalBox.Artical>();
        Node artList=elements.get(0).childNode(3).childNode(1);
        for (int i = 0; i < artList.childNodeSize()/2; i++) {
            Artical latest_art=new Artical();
            Node art_node=artList.childNode(2*i+1);
            String title=art_node.childNode(0).attr("title");
            String detail_path=art_node.childNode(0).attr("href");
            String publish_date=art_node.childNode(1).childNode(0)+"";

            latest_art.setTitle(title);
            latest_art.setDetailPath(detail_path);
            latest_art.setPublishDate(publish_date);
            latest_art.setOtherAttr("withDate");
            latest_arts.add(latest_art);
            System.out.println(""+latest_arts);
        }
        return latest_arts;
    }

    public static List<Artical> getSimpleArts(Elements elements){

        List<Artical> sim_arts=new ArrayList<ArticalBox.Artical>();
        for(int i=0;i<elements.size();i++){

            Node node=elements.get(i);
            String detail_path=node.childNode(1).childNode(0).attr("href");
            String art_title=node.childNode(1).childNode(0).attr("title");
            String author="";
            try {
                author=node.childNode(5).childNode(3).childNode(1).childNode(0)+"";
            }catch (Exception e){
                author="佚名";
            }

            String publicsh_date=node.childNode(5).childNode(5).childNode(0)+"";
            publicsh_date=publicsh_date.replace("日期：","");

            Artical sim_art=new Artical();
            sim_art.setTitle(art_title);
            sim_art.setAuthor(author);
            sim_art.setDetailPath(detail_path);
            sim_art.setPublishDate(publicsh_date);
            sim_art.setOtherAttr("withDate");
            sim_arts.add(sim_art);
            System.out.println(""+sim_art);
        }
        return sim_arts;
    }

    public static List<String> getPagePath(Elements options){
        List<String> ops=new ArrayList<String>();
        for (int i=0;i<options.size();i++){
            Element option=options.get(i);
            ops.add(option.attr("value"));
        }
        return  ops;
    }
}
