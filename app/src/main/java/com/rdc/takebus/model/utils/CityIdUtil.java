package com.rdc.takebus.model.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.rdc.takebus.entity.City;
import com.rdc.takebus.entity.CityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZZH on 2016/5/21.
 */
public class CityIdUtil {
    public static String jsonData = "{\"status\":\"0\",\"msg\":\"ok\",\"result\":[{\"cityid\":\"24\",\"name\":\"上海\",\"code\":\"shanghai\"},{\"cityid\":\"220\",\"name\":\"苏州\",\"code\":\"suzhou\"},{\"cityid\":\"226\",\"name\":\"宿迁\",\"code\":\"suqian\"},{\"cityid\":\"1913\",\"name\":\"沭阳\",\"code\":\"shuyang\"},{\"cityid\":\"1914\",\"name\":\"泗阳\",\"code\":\"siyang\"},{\"cityid\":\"388\",\"name\":\"绍兴\",\"code\":\"shaoxing\"},{\"cityid\":\"3290\",\"name\":\"上虞\",\"code\":\"shangyu\"},{\"cityid\":\"314\",\"name\":\"商洛\",\"code\":\"shangluo\"},{\"cityid\":\"368\",\"name\":\"普洱\",\"code\":\"puer\"},{\"cityid\":\"3110\",\"name\":\"石林\",\"code\":\"shilin\"},{\"cityid\":\"76\",\"name\":\"深圳\",\"code\":\"shenzhen\"},{\"cityid\":\"87\",\"name\":\"汕头\",\"code\":\"shantou\"},{\"cityid\":\"89\",\"name\":\"韶关\",\"code\":\"shaoguan\"},{\"cityid\":\"88\",\"name\":\"汕尾\",\"code\":\"shanwei\"},{\"cityid\":\"838\",\"name\":\"四会\",\"code\":\"sihui\"},{\"cityid\":\"243\",\"name\":\"沈阳\",\"code\":\"shenyang\"},{\"cityid\":\"58\",\"name\":\"三明\",\"code\":\"sanming\"},{\"cityid\":\"566\",\"name\":\"石狮\",\"code\":\"shishi\"},{\"cityid\":\"336\",\"name\":\"遂宁\",\"code\":\"suining\"},{\"cityid\":\"188\",\"name\":\"十堰\",\"code\":\"shiyan\"},{\"cityid\":\"189\",\"name\":\"随州\",\"code\":\"suizhou\"},{\"cityid\":\"176\",\"name\":\"双鸭山\",\"code\":\"shuangyashan\"},{\"cityid\":\"177\",\"name\":\"绥化\",\"code\":\"suihua\"},{\"cityid\":\"46\",\"name\":\"宿州\",\"code\":\"suzhou2\"},{\"cityid\":\"215\",\"name\":\"四平\",\"code\":\"siping\"},{\"cityid\":\"216\",\"name\":\"松原\",\"code\":\"songyuan\"},{\"cityid\":\"137\",\"name\":\"石家庄\",\"code\":\"shijiazhuang\"},{\"cityid\":\"306\",\"name\":\"朔州\",\"code\":\"shuozhou\"},{\"cityid\":\"120\",\"name\":\"三亚\",\"code\":\"sanya\"},{\"cityid\":\"2439\",\"name\":\"寿光\",\"code\":\"shouguang\"},{\"cityid\":\"157\",\"name\":\"三门峡\",\"code\":\"sanmenxia\"},{\"cityid\":\"158\",\"name\":\"商丘\",\"code\":\"shangqiu\"},{\"cityid\":\"203\",\"name\":\"邵阳\",\"code\":\"shaoyang\"},{\"cityid\":\"239\",\"name\":\"上饶\",\"code\":\"shangrao\"},{\"cityid\":\"26\",\"name\":\"天津\",\"code\":\"tianjin\"},{\"cityid\":\"227\",\"name\":\"泰州\",\"code\":\"taizhou2\"},{\"cityid\":\"1868\",\"name\":\"太仓\",\"code\":\"taicang\"},{\"cityid\":\"1920\",\"name\":\"泰兴\",\"code\":\"taixing\"},{\"cityid\":\"389\",\"name\":\"台州\",\"code\":\"taizhou\"},{\"cityid\":\"3252\",\"name\":\"桐乡\",\"code\":\"tongxiang\"},{\"cityid\":\"3240\",\"name\":\"桐庐\",\"code\":\"tonglu\"},{\"cityid\":\"315\",\"name\":\"铜川\",\"code\":\"tongchuan\"},{\"cityid\":\"3134\",\"name\":\"腾冲\",\"code\":\"tengchong\"},{\"cityid\":\"765\",\"name\":\"台山\",\"code\":\"taishan\"},{\"cityid\":\"255\",\"name\":\"铁岭\",\"code\":\"tieling\"},{\"cityid\":\"72\",\"name\":\"天水\",\"code\":\"tianshui\"},{\"cityid\":\"47\",\"name\":\"铜陵\",\"code\":\"tongling\"},{\"cityid\":\"426\",\"name\":\"天长\",\"code\":\"tianchang\"},{\"cityid\":\"217\",\"name\":\"通化\",\"code\":\"tonghua\"},{\"cityid\":\"363\",\"name\":\"吐鲁番\",\"code\":\"tulufan\"},{\"cityid\":\"145\",\"name\":\"唐山\",\"code\":\"tangshan\"},{\"cityid\":\"299\",\"name\":\"太原\",\"code\":\"taiyuan\"},{\"cityid\":\"264\",\"name\":\"通辽\",\"code\":\"tongliao\"},{\"cityid\":\"293\",\"name\":\"泰安\",\"code\":\"taian\"},{\"cityid\":\"2463\",\"name\":\"滕州\",\"code\":\"tengzhou\"},{\"cityid\":\"117\",\"name\":\"铜仁\",\"code\":\"tongren\"},{\"cityid\":\"1\",\"name\":\"北京\",\"code\":\"beijing\"},{\"cityid\":\"312\",\"name\":\"宝鸡\",\"code\":\"baoji\"},{\"cityid\":\"370\",\"name\":\"保山\",\"code\":\"baoshan\"},{\"cityid\":\"246\",\"name\":\"本溪\",\"code\":\"benxi\"},{\"cityid\":\"324\",\"name\":\"巴中\",\"code\":\"bazhong\"},{\"cityid\":\"62\",\"name\":\"白银\",\"code\":\"baiyin\"},{\"cityid\":\"36\",\"name\":\"蚌埠\",\"code\":\"bengbu\"},{\"cityid\":\"50\",\"name\":\"亳州\",\"code\":\"bozhou\"},{\"cityid\":\"213\",\"name\":\"白山\",\"code\":\"baishan\"},{\"cityid\":\"212\",\"name\":\"白城\",\"code\":\"baicheng\"},{\"cityid\":\"138\",\"name\":\"保定\",\"code\":\"baoding\"},{\"cityid\":\"260\",\"name\":\"包头\",\"code\":\"baotou\"},{\"cityid\":\"259\",\"name\":\"巴彦淖尔\",\"code\":\"bayannaoer\"},{\"cityid\":\"284\",\"name\":\"滨州\",\"code\":\"binzhou\"},{\"cityid\":\"99\",\"name\":\"北海\",\"code\":\"beihai\"},{\"cityid\":\"98\",\"name\":\"百色\",\"code\":\"baise\"},{\"cityid\":\"31\",\"name\":\"重庆\",\"code\":\"chongqing\"},{\"cityid\":\"222\",\"name\":\"常州\",\"code\":\"changzhou\"},{\"cityid\":\"1854\",\"name\":\"常熟\",\"code\":\"changshu\"},{\"cityid\":\"3285\",\"name\":\"慈溪\",\"code\":\"cixi\"},{\"cityid\":\"2632\",\"name\":\"城固\",\"code\":\"chenggu\"},{\"cityid\":\"371\",\"name\":\"楚雄\",\"code\":\"chuxiong\"},{\"cityid\":\"77\",\"name\":\"潮州\",\"code\":\"chaozhou\"},{\"cityid\":\"247\",\"name\":\"朝阳\",\"code\":\"chaoyang\"},{\"cityid\":\"2083\",\"name\":\"长海\",\"code\":\"changhai\"},{\"cityid\":\"523\",\"name\":\"长乐\",\"code\":\"changle\"},{\"cityid\":\"321\",\"name\":\"成都\",\"code\":\"chengdu\"},{\"cityid\":\"1604\",\"name\":\"赤壁\",\"code\":\"chibi\"},{\"cityid\":\"37\",\"name\":\"巢湖\",\"code\":\"chaohu\"},{\"cityid\":\"38\",\"name\":\"池州\",\"code\":\"chizhou\"},{\"cityid\":\"39\",\"name\":\"滁州\",\"code\":\"chuzhou\"},{\"cityid\":\"210\",\"name\":\"长春\",\"code\":\"changchun\"},{\"cityid\":\"355\",\"name\":\"昌吉\",\"code\":\"changji\"},{\"cityid\":\"140\",\"name\":\"承德\",\"code\":\"chengde\"},{\"cityid\":\"139\",\"name\":\"沧州\",\"code\":\"cangzhou\"},{\"cityid\":\"300\",\"name\":\"长治\",\"code\":\"changzhi\"},{\"cityid\":\"261\",\"name\":\"赤峰\",\"code\":\"chifeng\"},{\"cityid\":\"196\",\"name\":\"长沙\",\"code\":\"changsha\"},{\"cityid\":\"198\",\"name\":\"常德\",\"code\":\"changde\"},{\"cityid\":\"199\",\"name\":\"郴州\",\"code\":\"chenzhou\"},{\"cityid\":\"100\",\"name\":\"崇左\",\"code\":\"chongzuo\"},{\"cityid\":\"1041\",\"name\":\"赤水\",\"code\":\"chishui\"},{\"cityid\":\"32\",\"name\":\"香港\",\"code\":\"hongkong\"},{\"cityid\":\"223\",\"name\":\"淮安\",\"code\":\"huaian\"},{\"cityid\":\"1907\",\"name\":\"海门\",\"code\":\"haimen\"},{\"cityid\":\"382\",\"name\":\"杭州\",\"code\":\"hangzhou\"},{\"cityid\":\"383\",\"name\":\"湖州\",\"code\":\"huzhou\"},{\"cityid\":\"3249\",\"name\":\"海宁\",\"code\":\"haining\"},{\"cityid\":\"313\",\"name\":\"汉中\",\"code\":\"hanzhong\"},{\"cityid\":\"81\",\"name\":\"惠州\",\"code\":\"huizhou\"},{\"cityid\":\"80\",\"name\":\"河源\",\"code\":\"heyuan\"},{\"cityid\":\"251\",\"name\":\"葫芦岛\",\"code\":\"huludao\"},{\"cityid\":\"2089\",\"name\":\"海城\",\"code\":\"haicheng\"},{\"cityid\":\"182\",\"name\":\"黄冈\",\"code\":\"huanggang\"},{\"cityid\":\"183\",\"name\":\"黄石\",\"code\":\"huangshi\"},{\"cityid\":\"166\",\"name\":\"哈尔滨\",\"code\":\"haerbin\"},{\"cityid\":\"169\",\"name\":\"鹤岗\",\"code\":\"hegang\"},{\"cityid\":\"3400\",\"name\":\"合肥\",\"code\":\"hefei\"},{\"cityid\":\"41\",\"name\":\"淮北\",\"code\":\"huaibei\"},{\"cityid\":\"42\",\"name\":\"淮南\",\"code\":\"huainan\"},{\"cityid\":\"43\",\"name\":\"黄山\",\"code\":\"huangshan\"},{\"cityid\":\"356\",\"name\":\"哈密\",\"code\":\"hami\"},{\"cityid\":\"357\",\"name\":\"和田\",\"code\":\"hetian\"},{\"cityid\":\"142\",\"name\":\"衡水\",\"code\":\"hengshui\"},{\"cityid\":\"141\",\"name\":\"邯郸\",\"code\":\"handan\"},{\"cityid\":\"257\",\"name\":\"呼和浩特\",\"code\":\"huhehaote\"},{\"cityid\":\"263\",\"name\":\"呼伦贝尔\",\"code\":\"hulunbeier\"},{\"cityid\":\"119\",\"name\":\"海口\",\"code\":\"haikou\"},{\"cityid\":\"287\",\"name\":\"菏泽\",\"code\":\"heze\"},{\"cityid\":\"152\",\"name\":\"鹤壁\",\"code\":\"hebi\"},{\"cityid\":\"201\",\"name\":\"怀化\",\"code\":\"huaihua\"},{\"cityid\":\"200\",\"name\":\"衡阳\",\"code\":\"hengyang\"},{\"cityid\":\"103\",\"name\":\"河池\",\"code\":\"hechi\"},{\"cityid\":\"104\",\"name\":\"贺州\",\"code\":\"hezhou\"},{\"cityid\":\"3253\",\"name\":\"海盐\",\"code\":\"haiyan\"},{\"cityid\":\"33\",\"name\":\"澳门\",\"code\":\"aomen\"},{\"cityid\":\"311\",\"name\":\"安康\",\"code\":\"ankang\"},{\"cityid\":\"3104\",\"name\":\"安宁\",\"code\":\"anning\"},{\"cityid\":\"245\",\"name\":\"鞍山\",\"code\":\"anshan\"},{\"cityid\":\"35\",\"name\":\"安庆\",\"code\":\"anqing\"},{\"cityid\":\"351\",\"name\":\"阿克苏\",\"code\":\"akesu\"},{\"cityid\":\"151\",\"name\":\"安阳\",\"code\":\"anyang\"},{\"cityid\":\"111\",\"name\":\"安顺\",\"code\":\"anshun\"},{\"cityid\":\"219\",\"name\":\"南京\",\"code\":\"nanjing\"},{\"cityid\":\"225\",\"name\":\"南通\",\"code\":\"nantong\"},{\"cityid\":\"387\",\"name\":\"宁波\",\"code\":\"ningbo\"},{\"cityid\":\"3288\",\"name\":\"宁海\",\"code\":\"ninghai\"},{\"cityid\":\"55\",\"name\":\"宁德\",\"code\":\"ningde\"},{\"cityid\":\"568\",\"name\":\"南安\",\"code\":\"nanan\"},{\"cityid\":\"333\",\"name\":\"南充\",\"code\":\"nanchong\"},{\"cityid\":\"334\",\"name\":\"内江\",\"code\":\"neijiang\"},{\"cityid\":\"155\",\"name\":\"南阳\",\"code\":\"nanyang\"},{\"cityid\":\"232\",\"name\":\"南昌\",\"code\":\"nanchang\"},{\"cityid\":\"96\",\"name\":\"南宁\",\"code\":\"nanning\"},{\"cityid\":\"1655\",\"name\":\"宁乡\",\"code\":\"ningxiang\"},{\"cityid\":\"224\",\"name\":\"连云港\",\"code\":\"lianyungang\"},{\"cityid\":\"1884\",\"name\":\"溧阳\",\"code\":\"liyang\"},{\"cityid\":\"386\",\"name\":\"丽水\",\"code\":\"lishui\"},{\"cityid\":\"3239\",\"name\":\"临安\",\"code\":\"linan\"},{\"cityid\":\"3299\",\"name\":\"临海\",\"code\":\"linhai\"},{\"cityid\":\"376\",\"name\":\"临沧\",\"code\":\"lincang\"},{\"cityid\":\"369\",\"name\":\"丽江\",\"code\":\"lijiang\"},{\"cityid\":\"811\",\"name\":\"乐昌\",\"code\":\"lechang\"},{\"cityid\":\"790\",\"name\":\"连州\",\"code\":\"lianzhou\"},{\"cityid\":\"253\",\"name\":\"辽阳\",\"code\":\"liaoyang\"},{\"cityid\":\"53\",\"name\":\"龙岩\",\"code\":\"longyan\"},{\"cityid\":\"594\",\"name\":\"龙海\",\"code\":\"longhai\"},{\"cityid\":\"341\",\"name\":\"泸州\",\"code\":\"luzhou\"},{\"cityid\":\"61\",\"name\":\"兰州\",\"code\":\"lanzhou\"},{\"cityid\":\"68\",\"name\":\"临夏\",\"code\":\"linxia\"},{\"cityid\":\"44\",\"name\":\"六安\",\"code\":\"luan\"},{\"cityid\":\"214\",\"name\":\"辽源\",\"code\":\"liaoyuan\"},{\"cityid\":\"143\",\"name\":\"廊坊\",\"code\":\"langfang\"},{\"cityid\":\"305\",\"name\":\"吕梁\",\"code\":\"lvliang\"},{\"cityid\":\"304\",\"name\":\"临汾\",\"code\":\"linfen\"},{\"cityid\":\"291\",\"name\":\"临沂\",\"code\":\"linyi\"},{\"cityid\":\"290\",\"name\":\"聊城\",\"code\":\"liaocheng\"},{\"cityid\":\"289\",\"name\":\"莱芜\",\"code\":\"laiwu\"},{\"cityid\":\"2452\",\"name\":\"莱州\",\"code\":\"laizhou\"},{\"cityid\":\"2352\",\"name\":\"莱西\",\"code\":\"laixi\"},{\"cityid\":\"149\",\"name\":\"洛阳\",\"code\":\"luoyang\"},{\"cityid\":\"164\",\"name\":\"漯河\",\"code\":\"luohe\"},{\"cityid\":\"1342\",\"name\":\"灵宝\",\"code\":\"lingbao\"},{\"cityid\":\"202\",\"name\":\"娄底\",\"code\":\"loudi\"},{\"cityid\":\"1764\",\"name\":\"醴陵\",\"code\":\"liling\"},{\"cityid\":\"106\",\"name\":\"柳州\",\"code\":\"liuzhou\"},{\"cityid\":\"105\",\"name\":\"来宾\",\"code\":\"laibin\"},{\"cityid\":\"343\",\"name\":\"拉萨\",\"code\":\"lasa\"},{\"cityid\":\"113\",\"name\":\"六盘水\",\"code\":\"liupanshui\"},{\"cityid\":\"228\",\"name\":\"徐州\",\"code\":\"xuzhou\"},{\"cityid\":\"1918\",\"name\":\"兴化\",\"code\":\"xinghua\"},{\"cityid\":\"1927\",\"name\":\"新沂\",\"code\":\"xinyi\"},{\"cityid\":\"3287\",\"name\":\"象山\",\"code\":\"xiangshan\"},{\"cityid\":\"310\",\"name\":\"西安\",\"code\":\"xian\"},{\"cityid\":\"317\",\"name\":\"咸阳\",\"code\":\"xianyang\"},{\"cityid\":\"59\",\"name\":\"厦门\",\"code\":\"xiamen\"},{\"cityid\":\"192\",\"name\":\"襄阳\",\"code\":\"xiangyang\"},{\"cityid\":\"191\",\"name\":\"咸宁\",\"code\":\"xianning\"},{\"cityid\":\"193\",\"name\":\"孝感\",\"code\":\"xiaogan\"},{\"cityid\":\"49\",\"name\":\"宣城\",\"code\":\"xuancheng\"},{\"cityid\":\"146\",\"name\":\"邢台\",\"code\":\"xingtai\"},{\"cityid\":\"160\",\"name\":\"信阳\",\"code\":\"xinyang\"},{\"cityid\":\"159\",\"name\":\"新乡\",\"code\":\"xinxiang\"},{\"cityid\":\"161\",\"name\":\"许昌\",\"code\":\"xuchang\"},{\"cityid\":\"1384\",\"name\":\"项城\",\"code\":\"xiangcheng\"},{\"cityid\":\"204\",\"name\":\"湘潭\",\"code\":\"xiangtan\"},{\"cityid\":\"1723\",\"name\":\"湘乡\",\"code\":\"xiangxiang\"},{\"cityid\":\"240\",\"name\":\"新余\",\"code\":\"xinyu\"},{\"cityid\":\"274\",\"name\":\"西宁\",\"code\":\"xining\"},{\"cityid\":\"3293\",\"name\":\"新昌\",\"code\":\"xinchang\"},{\"cityid\":\"230\",\"name\":\"扬州\",\"code\":\"yangzhou\"},{\"cityid\":\"229\",\"name\":\"盐城\",\"code\":\"yancheng\"},{\"cityid\":\"1877\",\"name\":\"宜兴\",\"code\":\"yixing\"},{\"cityid\":\"1947\",\"name\":\"仪征\",\"code\":\"yizheng\"},{\"cityid\":\"1955\",\"name\":\"扬中\",\"code\":\"yangzhong\"},{\"cityid\":\"3257\",\"name\":\"义乌\",\"code\":\"yiwu\"},{\"cityid\":\"3284\",\"name\":\"余姚\",\"code\":\"yuyao\"},{\"cityid\":\"3300\",\"name\":\"玉环\",\"code\":\"yuhuan\"},{\"cityid\":\"3265\",\"name\":\"永康\",\"code\":\"yongkang\"},{\"cityid\":\"318\",\"name\":\"延安\",\"code\":\"yanan\"},{\"cityid\":\"319\",\"name\":\"榆林\",\"code\":\"yulin2\"},{\"cityid\":\"380\",\"name\":\"玉溪\",\"code\":\"yuxi\"},{\"cityid\":\"90\",\"name\":\"阳江\",\"code\":\"yangjiang\"},{\"cityid\":\"819\",\"name\":\"阳春\",\"code\":\"yangchun\"},{\"cityid\":\"91\",\"name\":\"云浮\",\"code\":\"yunfu\"},{\"cityid\":\"256\",\"name\":\"营口\",\"code\":\"yingkou\"},{\"cityid\":\"576\",\"name\":\"永安\",\"code\":\"yongan\"},{\"cityid\":\"338\",\"name\":\"宜宾\",\"code\":\"yibin\"},{\"cityid\":\"337\",\"name\":\"雅安\",\"code\":\"yaan\"},{\"cityid\":\"194\",\"name\":\"宜昌\",\"code\":\"yichang\"},{\"cityid\":\"178\",\"name\":\"伊春\",\"code\":\"yichun2\"},{\"cityid\":\"1825\",\"name\":\"延吉\",\"code\":\"yanji\"},{\"cityid\":\"218\",\"name\":\"延边\",\"code\":\"yanbian\"},{\"cityid\":\"3077\",\"name\":\"伊宁\",\"code\":\"yining\"},{\"cityid\":\"365\",\"name\":\"伊犁\",\"code\":\"yili\"},{\"cityid\":\"309\",\"name\":\"运城\",\"code\":\"yuncheng\"},{\"cityid\":\"308\",\"name\":\"阳泉\",\"code\":\"yangquan\"},{\"cityid\":\"2212\",\"name\":\"牙克石\",\"code\":\"yakeshi\"},{\"cityid\":\"296\",\"name\":\"烟台\",\"code\":\"yantai\"},{\"cityid\":\"2388\",\"name\":\"兖州\",\"code\":\"yanzhou\"},{\"cityid\":\"208\",\"name\":\"岳阳\",\"code\":\"yueyang\"},{\"cityid\":\"207\",\"name\":\"永州\",\"code\":\"yongzhou\"},{\"cityid\":\"206\",\"name\":\"益阳\",\"code\":\"yiyang\"},{\"cityid\":\"241\",\"name\":\"宜春\",\"code\":\"yichun\"},{\"cityid\":\"242\",\"name\":\"鹰潭\",\"code\":\"yingtan\"},{\"cityid\":\"269\",\"name\":\"银川\",\"code\":\"yinchuan\"},{\"cityid\":\"109\",\"name\":\"玉林\",\"code\":\"yulin\"},{\"cityid\":\"221\",\"name\":\"无锡\",\"code\":\"wuxi\"},{\"cityid\":\"1867\",\"name\":\"吴江\",\"code\":\"wujiang\"},{\"cityid\":\"390\",\"name\":\"温州\",\"code\":\"wenzhou\"},{\"cityid\":\"3298\",\"name\":\"温岭\",\"code\":\"wenling\"},{\"cityid\":\"316\",\"name\":\"渭南\",\"code\":\"weinan\"},{\"cityid\":\"2080\",\"name\":\"瓦房店\",\"code\":\"wafangdian\"},{\"cityid\":\"73\",\"name\":\"武威\",\"code\":\"wuwei\"},{\"cityid\":\"179\",\"name\":\"武汉\",\"code\":\"wuhan\"},{\"cityid\":\"48\",\"name\":\"芜湖\",\"code\":\"wuhu\"},{\"cityid\":\"350\",\"name\":\"乌鲁木齐\",\"code\":\"wulumuqi\"},{\"cityid\":\"1157\",\"name\":\"武安\",\"code\":\"wuan\"},{\"cityid\":\"265\",\"name\":\"乌海\",\"code\":\"wuhai\"},{\"cityid\":\"266\",\"name\":\"乌兰察布\",\"code\":\"wulanchabu\"},{\"cityid\":\"294\",\"name\":\"威海\",\"code\":\"weihai\"},{\"cityid\":\"295\",\"name\":\"潍坊\",\"code\":\"weifang\"},{\"cityid\":\"2432\",\"name\":\"文登\",\"code\":\"wendeng\"},{\"cityid\":\"272\",\"name\":\"吴忠\",\"code\":\"wuzhong\"},{\"cityid\":\"231\",\"name\":\"镇江\",\"code\":\"zhenjiang\"},{\"cityid\":\"1855\",\"name\":\"张家港\",\"code\":\"zhangjiagang\"},{\"cityid\":\"391\",\"name\":\"舟山\",\"code\":\"zhoushan\"},{\"cityid\":\"3294\",\"name\":\"诸暨\",\"code\":\"zhuji\"},{\"cityid\":\"381\",\"name\":\"昭通\",\"code\":\"zhaotong\"},{\"cityid\":\"95\",\"name\":\"珠海\",\"code\":\"zhuhai\"},{\"cityid\":\"94\",\"name\":\"中山\",\"code\":\"zhongshan\"},{\"cityid\":\"93\",\"name\":\"肇庆\",\"code\":\"zhaoqing\"},{\"cityid\":\"92\",\"name\":\"湛江\",\"code\":\"zhanjiang\"},{\"cityid\":\"701\",\"name\":\"增城\",\"code\":\"zengcheng\"},{\"cityid\":\"2082\",\"name\":\"庄河\",\"code\":\"zhuanghe\"},{\"cityid\":\"60\",\"name\":\"漳州\",\"code\":\"zhangzhou\"},{\"cityid\":\"340\",\"name\":\"自贡\",\"code\":\"zigong\"},{\"cityid\":\"339\",\"name\":\"资阳\",\"code\":\"ziyang\"},{\"cityid\":\"74\",\"name\":\"张掖\",\"code\":\"zhangye\"},{\"cityid\":\"1518\",\"name\":\"肇东\",\"code\":\"zhaodong\"},{\"cityid\":\"147\",\"name\":\"张家口\",\"code\":\"zhangjiakou\"},{\"cityid\":\"297\",\"name\":\"枣庄\",\"code\":\"zaozhuang\"},{\"cityid\":\"298\",\"name\":\"淄博\",\"code\":\"zibo\"},{\"cityid\":\"2337\",\"name\":\"章丘\",\"code\":\"zhangqiu\"},{\"cityid\":\"2438\",\"name\":\"诸城\",\"code\":\"zhucheng\"},{\"cityid\":\"148\",\"name\":\"郑州\",\"code\":\"zhengzhou\"},{\"cityid\":\"162\",\"name\":\"周口\",\"code\":\"zhoukou\"},{\"cityid\":\"163\",\"name\":\"驻马店\",\"code\":\"zhumadian\"},{\"cityid\":\"197\",\"name\":\"张家界\",\"code\":\"zhangjiajie\"},{\"cityid\":\"209\",\"name\":\"株洲\",\"code\":\"zhuzhou\"},{\"cityid\":\"118\",\"name\":\"遵义\",\"code\":\"zunyi\"},{\"cityid\":\"1853\",\"name\":\"昆山\",\"code\":\"kunshan\"},{\"cityid\":\"366\",\"name\":\"昆明\",\"code\":\"kunming\"},{\"cityid\":\"766\",\"name\":\"开平\",\"code\":\"kaiping\"},{\"cityid\":\"359\",\"name\":\"克拉玛依\",\"code\":\"kelamayi\"},{\"cityid\":\"358\",\"name\":\"喀什\",\"code\":\"kashi\"},{\"cityid\":\"3079\",\"name\":\"奎屯\",\"code\":\"kuitun\"},{\"cityid\":\"150\",\"name\":\"开封\",\"code\":\"kaifeng\"},{\"cityid\":\"1876\",\"name\":\"江阴\",\"code\":\"jiangyin\"},{\"cityid\":\"1919\",\"name\":\"靖江\",\"code\":\"jingjiang\"},{\"cityid\":\"1885\",\"name\":\"金坛\",\"code\":\"jintan\"},{\"cityid\":\"1956\",\"name\":\"句容\",\"code\":\"jurong\"},{\"cityid\":\"1921\",\"name\":\"姜堰\",\"code\":\"jiangyan\"},{\"cityid\":\"384\",\"name\":\"嘉兴\",\"code\":\"jiaxing\"},{\"cityid\":\"385\",\"name\":\"金华\",\"code\":\"jinhua\"},{\"cityid\":\"3250\",\"name\":\"嘉善\",\"code\":\"jiashan\"},{\"cityid\":\"82\",\"name\":\"江门\",\"code\":\"jiangmen\"},{\"cityid\":\"83\",\"name\":\"揭阳\",\"code\":\"jieyang\"},{\"cityid\":\"252\",\"name\":\"锦州\",\"code\":\"jinzhou\"},{\"cityid\":\"567\",\"name\":\"晋江\",\"code\":\"jinjiang\"},{\"cityid\":\"67\",\"name\":\"酒泉\",\"code\":\"jiuquan\"},{\"cityid\":\"65\",\"name\":\"嘉峪关\",\"code\":\"jiayuguan\"},{\"cityid\":\"66\",\"name\":\"金昌\",\"code\":\"jinchang\"},{\"cityid\":\"185\",\"name\":\"荆州\",\"code\":\"jingzhou\"},{\"cityid\":\"184\",\"name\":\"荆门\",\"code\":\"jingmen\"},{\"cityid\":\"172\",\"name\":\"佳木斯\",\"code\":\"jiamusi\"},{\"cityid\":\"171\",\"name\":\"鸡西\",\"code\":\"jixi\"},{\"cityid\":\"211\",\"name\":\"吉林\",\"code\":\"jilin2\"},{\"cityid\":\"302\",\"name\":\"晋城\",\"code\":\"jincheng\"},{\"cityid\":\"303\",\"name\":\"晋中\",\"code\":\"jinzhong\"},{\"cityid\":\"2516\",\"name\":\"介休\",\"code\":\"jiexiu\"},{\"cityid\":\"282\",\"name\":\"济南\",\"code\":\"jinan\"},{\"cityid\":\"288\",\"name\":\"济宁\",\"code\":\"jining\"},{\"cityid\":\"2348\",\"name\":\"胶州\",\"code\":\"jiaozhou\"},{\"cityid\":\"2349\",\"name\":\"即墨\",\"code\":\"jimo\"},{\"cityid\":\"154\",\"name\":\"焦作\",\"code\":\"jiaozuo\"},{\"cityid\":\"153\",\"name\":\"济源\",\"code\":\"jiyuan\"},{\"cityid\":\"237\",\"name\":\"九江\",\"code\":\"jiujiang\"},{\"cityid\":\"235\",\"name\":\"吉安\",\"code\":\"jian\"},{\"cityid\":\"236\",\"name\":\"景德镇\",\"code\":\"jingdezhen\"},{\"cityid\":\"1954\",\"name\":\"丹阳\",\"code\":\"danyang\"},{\"cityid\":\"1937\",\"name\":\"东台\",\"code\":\"dongtai\"},{\"cityid\":\"3264\",\"name\":\"东阳\",\"code\":\"dongyang\"},{\"cityid\":\"372\",\"name\":\"大理\",\"code\":\"dali\"},{\"cityid\":\"78\",\"name\":\"东莞\",\"code\":\"dongguan\"},{\"cityid\":\"244\",\"name\":\"大连\",\"code\":\"dalian\"},{\"cityid\":\"248\",\"name\":\"丹东\",\"code\":\"dandong\"},{\"cityid\":\"326\",\"name\":\"德阳\",\"code\":\"deyang\"},{\"cityid\":\"325\",\"name\":\"达州\",\"code\":\"dazhou\"},{\"cityid\":\"2732\",\"name\":\"都江堰\",\"code\":\"dujiangyan\"},{\"cityid\":\"63\",\"name\":\"定西\",\"code\":\"dingxi\"},{\"cityid\":\"167\",\"name\":\"大庆\",\"code\":\"daqing\"},{\"cityid\":\"1827\",\"name\":\"敦化\",\"code\":\"dunhua\"},{\"cityid\":\"301\",\"name\":\"大同\",\"code\":\"datong\"},{\"cityid\":\"286\",\"name\":\"东营\",\"code\":\"dongying\"},{\"cityid\":\"285\",\"name\":\"德州\",\"code\":\"dezhou\"},{\"cityid\":\"1007\",\"name\":\"都匀\",\"code\":\"duyun\"},{\"cityid\":\"3346\",\"name\":\"大足\",\"code\":\"dazu\"},{\"cityid\":\"1904\",\"name\":\"启东\",\"code\":\"qidong\"},{\"cityid\":\"392\",\"name\":\"衢州\",\"code\":\"quzhou\"},{\"cityid\":\"377\",\"name\":\"曲靖\",\"code\":\"qujing\"},{\"cityid\":\"86\",\"name\":\"清远\",\"code\":\"qingyuan\"},{\"cityid\":\"57\",\"name\":\"泉州\",\"code\":\"quanzhou\"},{\"cityid\":\"71\",\"name\":\"庆阳\",\"code\":\"qingyang\"},{\"cityid\":\"175\",\"name\":\"齐齐哈尔\",\"code\":\"qiqihaer\"},{\"cityid\":\"144\",\"name\":\"秦皇岛\",\"code\":\"qinhuangdao\"},{\"cityid\":\"1207\",\"name\":\"迁安\",\"code\":\"qianan\"},{\"cityid\":\"283\",\"name\":\"青岛\",\"code\":\"qingdao\"},{\"cityid\":\"2437\",\"name\":\"青州\",\"code\":\"qingzhou\"},{\"cityid\":\"107\",\"name\":\"钦州\",\"code\":\"qinzhou\"},{\"cityid\":\"115\",\"name\":\"黔南\",\"code\":\"qiannan\"},{\"cityid\":\"969\",\"name\":\"清镇\",\"code\":\"qingzhen\"},{\"cityid\":\"3343\",\"name\":\"綦江\",\"code\":\"qijiang\"},{\"cityid\":\"1948\",\"name\":\"高邮\",\"code\":\"gaoyou\"},{\"cityid\":\"1897\",\"name\":\"赣榆\",\"code\":\"ganyu\"},{\"cityid\":\"75\",\"name\":\"广州\",\"code\":\"guangzhou\"},{\"cityid\":\"776\",\"name\":\"高州\",\"code\":\"gaozhou\"},{\"cityid\":\"329\",\"name\":\"广元\",\"code\":\"guangyuan\"},{\"cityid\":\"328\",\"name\":\"广安\",\"code\":\"guangan\"},{\"cityid\":\"1261\",\"name\":\"巩义\",\"code\":\"gongyi\"},{\"cityid\":\"234\",\"name\":\"赣州\",\"code\":\"ganzhou\"},{\"cityid\":\"97\",\"name\":\"桂林\",\"code\":\"guilin\"},{\"cityid\":\"102\",\"name\":\"贵港\",\"code\":\"guigang\"},{\"cityid\":\"110\",\"name\":\"贵阳\",\"code\":\"guiyang\"},{\"cityid\":\"1905\",\"name\":\"如皋\",\"code\":\"rugao\"},{\"cityid\":\"3307\",\"name\":\"瑞安\",\"code\":\"ruian\"},{\"cityid\":\"292\",\"name\":\"日照\",\"code\":\"rizhao\"},{\"cityid\":\"2429\",\"name\":\"荣成\",\"code\":\"rongcheng\"},{\"cityid\":\"2430\",\"name\":\"乳山\",\"code\":\"rushan\"},{\"cityid\":\"348\",\"name\":\"日喀则\",\"code\":\"rikaze\"},{\"cityid\":\"1928\",\"name\":\"邳州\",\"code\":\"pizhou\"},{\"cityid\":\"3251\",\"name\":\"平湖\",\"code\":\"pinghu\"},{\"cityid\":\"770\",\"name\":\"普宁\",\"code\":\"puning\"},{\"cityid\":\"254\",\"name\":\"盘锦\",\"code\":\"panjin\"},{\"cityid\":\"2081\",\"name\":\"普兰店\",\"code\":\"pulandian\"},{\"cityid\":\"56\",\"name\":\"莆田\",\"code\":\"putian\"},{\"cityid\":\"335\",\"name\":\"攀枝花\",\"code\":\"panzhihua\"},{\"cityid\":\"70\",\"name\":\"平凉\",\"code\":\"pingliang\"},{\"cityid\":\"2350\",\"name\":\"平度\",\"code\":\"pingdu\"},{\"cityid\":\"2453\",\"name\":\"蓬莱\",\"code\":\"penglai\"},{\"cityid\":\"156\",\"name\":\"平顶山\",\"code\":\"pingdingshan\"},{\"cityid\":\"165\",\"name\":\"濮阳\",\"code\":\"puyang\"},{\"cityid\":\"238\",\"name\":\"萍乡\",\"code\":\"pingxiang\"},{\"cityid\":\"2524\",\"name\":\"平遥\",\"code\":\"pingyao\"},{\"cityid\":\"3238\",\"name\":\"富阳\",\"code\":\"fuyang2\"},{\"cityid\":\"3286\",\"name\":\"奉化\",\"code\":\"fenghua\"},{\"cityid\":\"79\",\"name\":\"佛山\",\"code\":\"foshan\"},{\"cityid\":\"249\",\"name\":\"抚顺\",\"code\":\"fushun\"},{\"cityid\":\"250\",\"name\":\"阜新\",\"code\":\"fuxin\"},{\"cityid\":\"52\",\"name\":\"福州\",\"code\":\"fuzhou\"},{\"cityid\":\"40\",\"name\":\"阜阳\",\"code\":\"fuyang\"},{\"cityid\":\"2426\",\"name\":\"肥城\",\"code\":\"feicheng\"},{\"cityid\":\"233\",\"name\":\"抚州\",\"code\":\"fuzhou2\"},{\"cityid\":\"101\",\"name\":\"防城港\",\"code\":\"fangchenggang\"},{\"cityid\":\"3336\",\"name\":\"涪陵\",\"code\":\"fuling\"},{\"cityid\":\"2635\",\"name\":\"勉县\",\"code\":\"mianxian\"},{\"cityid\":\"84\",\"name\":\"茂名\",\"code\":\"maoming\"},{\"cityid\":\"85\",\"name\":\"梅州\",\"code\":\"meizhou\"},{\"cityid\":\"322\",\"name\":\"绵阳\",\"code\":\"mianyang\"},{\"cityid\":\"332\",\"name\":\"眉山\",\"code\":\"meishan\"},{\"cityid\":\"173\",\"name\":\"牡丹江\",\"code\":\"mudanjiang\"},{\"cityid\":\"45\",\"name\":\"马鞍山\",\"code\":\"maanshan\"},{\"cityid\":\"427\",\"name\":\"明光\",\"code\":\"mingguang\"},{\"cityid\":\"1820\",\"name\":\"梅河口\",\"code\":\"meihekou\"},{\"cityid\":\"195\",\"name\":\"恩施\",\"code\":\"enshi\"},{\"cityid\":\"181\",\"name\":\"鄂州\",\"code\":\"ezhou\"},{\"cityid\":\"262\",\"name\":\"鄂尔多斯\",\"code\":\"eerduosi\"},{\"cityid\":\"54\",\"name\":\"南平\",\"code\":\"nanping\"},{\"cityid\":\"330\",\"name\":\"乐山\",\"code\":\"leshan\"},{\"cityid\":\"108\",\"name\":\"梧州\",\"code\":\"wuzhou\"},{\"cityid\":\"174\",\"name\":\"七台河\",\"code\":\"qitaihe\"}]}";
    public static List<City> listCity = new ArrayList<City>();


    public static void initListCity() {
        Gson gson = new Gson();
        CityModel cityModel = gson.fromJson(jsonData, CityModel.class);
        listCity = cityModel.getResult();
        Log.e("city id", listCity.size() + ">>");
    }

    //检索城市Id
    public static int getCityId(String cityName) {
        for (City city : listCity) {
            if (city.getName().equals(cityName))
                return city.getCityid();
        }
        return 0;
    }
}
