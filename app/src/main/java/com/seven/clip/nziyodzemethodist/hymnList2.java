package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class hymnList2 extends AppCompatActivity {

    ListView listView;
    HymnListScroll scroll;
    TextView pop;
    Handler timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn_list2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final Intent toHymn = new Intent(this,hymnDisplay.class);
        final Intent toFav = new Intent(this,MakeFav.class);
        toFav.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Data recFlag = new Data(this, "recordflag");
        Data favIt = new Data(this,"faviterator");
        Data recIt = new Data(this,"reciterator");
        favIt.update("0");
        recIt.update("0");
        recFlag.deleteAll();



        pop = (TextView) findViewById(R.id.hymnScrollPop);
        timer = new Handler();
        scroll = new HymnListScroll();
        View back = findViewById(R.id.hymnListBackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        final String [] sample = new String[317];
        final String [] sorted = new String[317];
        for(int i=0;i<317;i++)
            sample[i] = getStringResourceByName("hymn"+String.valueOf(i+1)+"firstline");
        for(int i=0;i<317;i++)
            sorted[i] = String.valueOf(i+1);
        Arrays.sort(sample);

        sorted[0]="235";
        sorted[1]="155";
        sorted[2]="72";
        sorted[3]="258";
        sorted[4]="116";
        sorted[5]="112";
        sorted[6]="226";
        sorted[7]="238";
        sorted[8]="213";
        sorted[9]="172";
        sorted[10]="161";
        sorted[11]="109";
        sorted[12]="279";
        sorted[13]="73";
        sorted[14]="200";
        sorted[15]="50";
        sorted[16]="79";
        sorted[17]="17";
        sorted[18]="290";
        sorted[19]="160";
        sorted[20]="255";
        sorted[21]="81";
        sorted[22]="243";
        sorted[23]="169";
        sorted[24]="250";
        sorted[25]="178";
        sorted[26]="95";
        sorted[27]="139";
        sorted[28]="168";
        sorted[29]="293";
        sorted[30]="48";
        sorted[31]="113";
        sorted[32]="314";
        sorted[33]="90";
        sorted[34]="303";
        sorted[35]="181";
        sorted[36]="193";
        sorted[37]="14";
        sorted[38]="144";
        sorted[39]="261";
        sorted[40]="234";
        sorted[41]="87";
        sorted[42]="128";
        sorted[43]="27";
        sorted[44]="194";
        sorted[45]="29";
        sorted[46]="30";
        sorted[47]="118";
        sorted[48]="19";
        sorted[49]="154";
        sorted[50]="281";
        sorted[51]="300";
        sorted[52]="295";
        sorted[53]="260";
        sorted[54]="216";
        sorted[55]="125";
        sorted[56]="210";
        sorted[57]="209";
        sorted[58]="177";
        sorted[59]="284";
        sorted[60]="257";
        sorted[61]="288";
        sorted[62]="99";
        sorted[63]="21";
        sorted[64]="253";
        sorted[65]="86";
        sorted[66]="7";
        sorted[67]="204";
        sorted[68]="176";
        sorted[69]="186";
        sorted[70]="313";
        sorted[71]="150";
        sorted[72]="317";
        sorted[73]="307";
        sorted[74]="275";
        sorted[75]="276";
        sorted[76]="115";
        sorted[77]="58";
        sorted[78]="197";
        sorted[79]="147";
        sorted[80]="20";
        sorted[81]="271";
        sorted[82]="241";
        sorted[83]="192";
        sorted[84]="269";
        sorted[85]="104";
        sorted[86]="45";
        sorted[87]="70";
        sorted[88]="282";
        sorted[89]="163";
        sorted[90]="248";
        sorted[91]="246";
        sorted[92]="162";
        sorted[93]="61";
        sorted[94]="233";
        sorted[95]="64";
        sorted[96]="36";
        sorted[97]="111";
        sorted[98]="4";
        sorted[99]="262";
        sorted[100]="2";
        sorted[101]="298";
        sorted[102]="170";
        sorted[103]="171";
        sorted[104]="267";
        sorted[105]="62";
        sorted[106]="148";
        sorted[107]="195";
        sorted[108]="100";
        sorted[109]="40";
        sorted[110]="237";
        sorted[111]="67";
        sorted[112]="132";
        sorted[113]="8";
        sorted[114]="23";
        sorted[115]="59";
        sorted[116]="236";
        sorted[117]="130";
        sorted[118]="239";
        sorted[119]="289";
        sorted[120]="286";
        sorted[121]="152";
        sorted[122]="285";
        sorted[123]="10";
        sorted[124]="266";
        sorted[125]="292";
        sorted[126]="120";
        sorted[127]="32";
        sorted[128]="63";
        sorted[129]="6";
        sorted[130]="201";
        sorted[131]="33";
        sorted[132]="143";
        sorted[133]="296";
        sorted[134]="191";
        sorted[135]="25";
        sorted[136]="13";
        sorted[137]="15";
        sorted[138]="207";
        sorted[139]="208";
        sorted[140]="199";
        sorted[141]="185";
        sorted[142]="56";
        sorted[143]="221";
        sorted[144]="187";
        sorted[145]="299";
        sorted[146]="38";
        sorted[147]="264";
        sorted[148]="110";
        sorted[149]="259";
        sorted[150]="230";
        sorted[151]="308";
        sorted[152]="265";
        sorted[153]="218";
        sorted[154]="297";
        sorted[155]="9";
        sorted[156]="190";
        sorted[157]="245";
        sorted[158]="146";
        sorted[159]="3";
        sorted[160]="158";
        sorted[161]="91";
        sorted[162]="156";
        sorted[163]="134";
        sorted[164]="263";
        sorted[165]="214";
        sorted[166]="287";
        sorted[167]="106";
        sorted[168]="77";
        sorted[169]="83";
        sorted[170]="84";
        sorted[171]="93";
        sorted[172]="82";
        sorted[173]="129";
        sorted[174]="316";
        sorted[175]="41";
        sorted[176]="196";
        sorted[177]="85";
        sorted[178]="69";
        sorted[179]="105";
        sorted[180]="60";
        sorted[181]="94";
        sorted[182]="157";
        sorted[183]="44";
        sorted[184]="167";
        sorted[185]="75";
        sorted[186]="68";
        sorted[187]="114";
        sorted[188]="141";
        sorted[189]="153";
        sorted[190]="294";
        sorted[191]="26";
        sorted[192]="12";
        sorted[193]="149";
        sorted[194]="278";
        sorted[195]="117";
        sorted[196]="188";
        sorted[197]="283";
        sorted[198]="47";
        sorted[199]="49";
        sorted[200]="24";
        sorted[201]="131";
        sorted[202]="280";
        sorted[203]="124";
        sorted[204]="277";
        sorted[205]="310";
        sorted[206]="179";
        sorted[207]="122";
        sorted[208]="16";
        sorted[209]="256";
        sorted[210]="5";
        sorted[211]="242";
        sorted[212]="55";
        sorted[213]="137";
        sorted[214]="222";
        sorted[215]="232";
        sorted[216]="211";
        sorted[217]="142";
        sorted[218]="309";
        sorted[219]="189";
        sorted[220]="198";
        sorted[221]="66";
        sorted[222]="121";
        sorted[223]="165";
        sorted[224]="203";
        sorted[225]="123";
        sorted[226]="227";
        sorted[227]="98";
        sorted[228]="205";
        sorted[229]="206";
        sorted[230]="108";
        sorted[231]="57";
        sorted[232]="34";
        sorted[233]="1";
        sorted[234]="138";
        sorted[235]="51";
        sorted[236]="174";
        sorted[237]="315";
        sorted[238]="126";
        sorted[239]="31";
        sorted[240]="164";
        sorted[241]="76";
        sorted[242]="175";
        sorted[243]="133";
        sorted[244]="127";
        sorted[245]="80";
        sorted[246]="251";
        sorted[247]="101";
        sorted[248]="92";
        sorted[249]="220";
        sorted[250]="97";
        sorted[251]="42";
        sorted[252]="39";
        sorted[253]="22";
        sorted[254]="312";
        sorted[255]="215";
        sorted[256]="223";
        sorted[257]="182";
        sorted[258]="228";
        sorted[259]="249";
        sorted[260]="184";
        sorted[261]="217";
        sorted[262]="136";
        sorted[263]="89";
        sorted[264]="173";
        sorted[265]="225";
        sorted[266]="151";
        sorted[267]="65";
        sorted[268]="301";
        sorted[269]="304";
        sorted[270]="224";
        sorted[271]="231";
        sorted[272]="43";
        sorted[273]="78";
        sorted[274]="311";
        sorted[275]="252";
        sorted[276]="254";
        sorted[277]="229";
        sorted[278]="102";
        sorted[279]="244";
        sorted[280]="247";
        sorted[281]="305";
        sorted[282]="18";
        sorted[283]="166";
        sorted[284]="54";
        sorted[285]="11";
        sorted[286]="71";
        sorted[287]="183";
        sorted[288]="268";
        sorted[289]="107";
        sorted[290]="28";
        sorted[291]="53";
        sorted[292]="302";
        sorted[293]="270";
        sorted[294]="272";
        sorted[295]="37";
        sorted[296]="35";
        sorted[297]="46";
        sorted[298]="88";
        sorted[299]="180";
        sorted[300]="273";
        sorted[301]="103";
        sorted[302]="140";
        sorted[303]="145";
        sorted[304]="159";
        sorted[305]="240";
        sorted[306]="96";
        sorted[307]="52";
        sorted[308]="202";
        sorted[309]="74";
        sorted[310]="291";
        sorted[311]="306";
        sorted[312]="274";
        sorted[313]="135";
        sorted[314]="212";
        sorted[315]="119";
        sorted[316]="219";

        MyHymnListAdapter adapter =
                new MyHymnListAdapter(
                        this,
                        sample
                );
        listView = (ListView) findViewById(R.id.hymnList);
        listView.setAdapter(adapter);


       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toHymn.putExtra("hymnNum",sorted[i]);
                startActivity(toHymn);
                //QuickToast(Integer.toString(i));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                toFav.putExtra("hymnNum",sorted[i]);
                startActivity(toFav);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                return true;
            }
        });*/
    }
    public void vis(View v){
        v.setAlpha(0f);
        v.setVisibility(View.VISIBLE);
        v.animate().alpha(1f);
    }
    public void invis(final View v) {
        v.animate().alpha(0f).withEndAction(new Runnable() {
            @Override
            public void run() {
                v.setVisibility(View.INVISIBLE);
            }
        });
    }


    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    scroll.up(listView.getFirstVisiblePosition());
                    showPop(scroll.letter());
                    listView.smoothScrollToPositionFromTop(scroll.pos(),0,400);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    scroll.down(listView.getFirstVisiblePosition());
                    showPop(scroll.letter());
                    listView.smoothScrollToPositionFromTop(scroll.pos(),0,100);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    public void showPop(String a) {
        Runnable rr = new Runnable() {
            @Override
            public void run() {invis(pop);}
        };
        pop.setText(a);
        timer.removeCallbacks(rr);
        vis(pop);
        timer.postDelayed(rr,3000);
    }

    public Context getActivity() {
        return this;
    }
    public void QuickToast(String s){
        Toast.makeText(getActivity(), s,
                Toast.LENGTH_SHORT).show();
    }

}
