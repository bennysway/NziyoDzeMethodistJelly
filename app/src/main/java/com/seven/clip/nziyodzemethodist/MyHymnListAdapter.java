package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;


class MyHymnListAdapter extends ArrayAdapter implements SectionIndexer {
    MyHymnListAdapter(Context context, String[] values) {
        super(context, R.layout.hymn_list2, values);

        alphaIndexer = new HashMap<>();
        positionIndexer = new HashMap<>();

        int size = values.length;
        for (int x = 0; x < size; x++) {
            String s = values[x];
            // get the first letter of the store
            String ch = s.substring(0, 1);
            // convert to uppercase otherwise lowercase a -z will be sorted
            // after upper A-Z
            ch = ch.toUpperCase();
            // put only if the key does not exist
            if (!alphaIndexer.containsKey(ch))
                alphaIndexer.put(ch, x);
        }
        Set<String> sectionLetters = alphaIndexer.keySet();
        ArrayList<String> sectionList = new ArrayList<>(
                sectionLetters);

        Collections.sort(sectionList);

        sections = new String[sectionList.size()];

        sectionList.toArray(sections);


        ArrayList numberVals = new ArrayList();
        for (int i : alphaIndexer.values()) {
            numberVals.add(i);
        }
        numberVals.add(values.length-1);
        Collections.sort(numberVals);

        int k = 0;
        int z = 0;
        for(int i = 0; i < numberVals.size()-1; i++) {
            int temp = (int) numberVals.get(i+1);
            do {
                positionIndexer.put(k, z);
                k++;
            } while(k < temp);
            z++;
        }



    }


    private HashMap<String, Integer> alphaIndexer;
    private HashMap<Integer, Integer> positionIndexer;
    private String[] sections;
    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater theInflater = (LayoutInflater.from(getContext()));
        View theView = theInflater.inflate(R.layout.hymn_list3, parent, false);
        String hymnEntry = (String) getItem(position);

        Button theTextView = (Button) theView.findViewById(R.id.hymnFirstLinebut3);
        ImageView enLight = (ImageView) theView.findViewById(R.id.en3);
        EnResource isEn = new EnResource(super.getContext());
        theTextView.setText(hymnEntry);
        Data favList = new Data(MyHymnListAdapter.super.getContext(), "favlist");
        String list = favList.get();
        String enList = isEn.getList();
        String [] en = enList.split(",");
        String[] favs = list.split(",");
        int counter = 0;
        int enCounter = 0;


        for (int i = 0; i < list.length(); i++) {
            if (list.charAt(i) == ',') {
                counter++;
            }
        }

        for (int i = 0; i < enList.length(); i++) {
            if (enList.charAt(i) == ',') {
                enCounter++;
            }
        }

        for (int i = 0; i < counter; i++) {
            favs[i] = String.valueOf(cvt(Integer.valueOf(favs[i])));
        }

        for (int i = 0; i < enCounter; i++) {
            en[i] = String.valueOf(cvt(Integer.valueOf(en[i])));
        }

        for (int i = 0; i < counter; i++) {
            if (Integer.parseInt(favs[i]) == position + 1) {
                theTextView.setTextColor(Color.parseColor("#ffffff"));
                theTextView.setShadowLayer(20.0f, 0.0f, 15.0f, Color.parseColor("#ffffff"));
            }
        }

        for (int i = 0; i < enCounter; i++) {
            if (Integer.parseInt(en[i]) == position + 1) {
                enLight.setAlpha(.4f);
            }
        }

        theTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHymn = new Intent(MyHymnListAdapter.super.getContext(),hymnDisplay.class);
                toHymn.putExtra("hymnNum",String.valueOf(hym(position)));
                getContext().startActivity(toHymn);
            }
        });

        theTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent toHymn = new Intent(MyHymnListAdapter.super.getContext(),MakeFav.class);
                toHymn.putExtra("hymnNum",String.valueOf(hym(position)));
                getContext().startActivity(toHymn);
                return true;
            }
        });

        return theView;
    }

    private int cvt(int x){
        int sorted[] = new int[321];
        int found=0;
        sorted[0]=235;
        sorted[1]=155;
        sorted[2]=72;
        sorted[3]=258;
        sorted[4]=116;
        sorted[5]=112;
        sorted[6]=226;
        sorted[7]=238;
        sorted[8]=213;
        sorted[9]=172;
        sorted[10]=161;
        sorted[11]=109;
        sorted[12]=279;
        sorted[13]=73;
        sorted[14]=200;
        sorted[15]=50;
        sorted[16]=79;
        sorted[17]=17;
        sorted[18]=290;
        sorted[19]=160;
        sorted[20]=255;
        sorted[21]=81;
        sorted[22]=243;
        sorted[23]=169;
        sorted[24]=250;
        sorted[25]=178;
        sorted[26]=95;
        sorted[27]=139;
        sorted[28]=168;
        sorted[29]=293;
        sorted[30]=48;
        sorted[31]=113;
        sorted[32]=314;
        sorted[33]=90;
        sorted[34]=303;
        sorted[35]=181;
        sorted[36]=193;
        sorted[37]=14;
        sorted[38]=144;
        sorted[39]=261;
        sorted[40]=234;
        sorted[41]=87;
        sorted[42]=128;
        sorted[43]=27;
        sorted[44]=194;
        sorted[45]=29;
        sorted[46]=30;
        sorted[47]=118;
        sorted[48]=19;
        sorted[49]=154;
        sorted[50]=281;
        sorted[51]=300;
        sorted[52]=295;
        sorted[53]=260;
        sorted[54]=216;
        sorted[55]=125;
        sorted[56]=210;
        sorted[57]=209;
        sorted[58]=177;
        sorted[59]=284;
        sorted[60]=257;
        sorted[61]=288;
        sorted[62]=99;
        sorted[63]=318;
        sorted[64]=21;
        sorted[65]=253;
        sorted[66]=86;
        sorted[67]=7;
        sorted[68]=204;
        sorted[69]=176;
        sorted[70]=186;
        sorted[71]=313;
        sorted[72]=150;
        sorted[73]=317;
        sorted[74]=307;
        sorted[75]=275;
        sorted[76]=276;
        sorted[77]=115;
        sorted[78]=58;
        sorted[79]=197;
        sorted[80]=147;
        sorted[81]=20;
        sorted[82]=271;
        sorted[83]=241;
        sorted[84]=192;
        sorted[85]=269;
        sorted[86]=104;
        sorted[87]=45;
        sorted[88]=70;
        sorted[89]=282;
        sorted[90]=163;
        sorted[91]=248;
        sorted[92]=246;
        sorted[93]=162;
        sorted[94]=61;
        sorted[95]=233;
        sorted[96]=64;
        sorted[97]=36;
        sorted[98]=111;
        sorted[99]=4;
        sorted[100]=262;
        sorted[101]=2;
        sorted[102]=298;
        sorted[103]=170;
        sorted[104]=171;
        sorted[105]=267;
        sorted[106]=62;
        sorted[107]=148;
        sorted[108]=195;
        sorted[109]=100;
        sorted[110]=40;
        sorted[111]=237;
        sorted[112]=67;
        sorted[113]=132;
        sorted[114]=8;
        sorted[115]=23;
        sorted[116]=59;
        sorted[117]=236;
        sorted[118]=130;
        sorted[119]=239;
        sorted[120]=289;
        sorted[121]=286;
        sorted[122]=152;
        sorted[123]=285;
        sorted[124]=10;
        sorted[125]=266;
        sorted[126]=292;
        sorted[127]=120;
        sorted[128]=32;
        sorted[129]=63;
        sorted[130]=6;
        sorted[131]=201;
        sorted[132]=33;
        sorted[133]=143;
        sorted[134]=296;
        sorted[135]=191;
        sorted[136]=25;
        sorted[137]=13;
        sorted[138]=15;
        sorted[139]=207;
        sorted[140]=208;
        sorted[141]=199;
        sorted[142]=185;
        sorted[143]=56;
        sorted[144]=221;
        sorted[145]=187;
        sorted[146]=320;
        sorted[147]=299;
        sorted[148]=38;
        sorted[149]=264;
        sorted[150]=110;
        sorted[151]=259;
        sorted[152]=230;
        sorted[153]=308;
        sorted[154]=218;
        sorted[155]=265;
        sorted[156]=297;
        sorted[157]=9;
        sorted[158]=190;
        sorted[159]=245;
        sorted[160]=146;
        sorted[161]=3;
        sorted[162]=158;
        sorted[163]=91;
        sorted[164]=156;
        sorted[165]=134;
        sorted[166]=263;
        sorted[167]=214;
        sorted[168]=287;
        sorted[169]=106;
        sorted[170]=77;
        sorted[171]=83;
        sorted[172]=84;
        sorted[173]=93;
        sorted[174]=82;
        sorted[175]=129;
        sorted[176]=316;
        sorted[177]=41;
        sorted[178]=196;
        sorted[179]=85;
        sorted[180]=69;
        sorted[181]=105;
        sorted[182]=60;
        sorted[183]=94;
        sorted[184]=157;
        sorted[185]=44;
        sorted[186]=167;
        sorted[187]=75;
        sorted[188]=68;
        sorted[189]=114;
        sorted[190]=141;
        sorted[191]=153;
        sorted[192]=294;
        sorted[193]=26;
        sorted[194]=12;
        sorted[195]=319;
        sorted[196]=149;
        sorted[197]=278;
        sorted[198]=117;
        sorted[199]=188;
        sorted[200]=283;
        sorted[201]=47;
        sorted[202]=49;
        sorted[203]=24;
        sorted[204]=131;
        sorted[205]=280;
        sorted[206]=124;
        sorted[207]=277;
        sorted[208]=310;
        sorted[209]=179;
        sorted[210]=122;
        sorted[211]=16;
        sorted[212]=256;
        sorted[213]=5;
        sorted[214]=242;
        sorted[215]=55;
        sorted[216]=137;
        sorted[217]=222;
        sorted[218]=232;
        sorted[219]=211;
        sorted[220]=142;
        sorted[221]=309;
        sorted[222]=189;
        sorted[223]=198;
        sorted[224]=66;
        sorted[225]=121;
        sorted[226]=165;
        sorted[227]=203;
        sorted[228]=123;
        sorted[229]=227;
        sorted[230]=98;
        sorted[231]=205;
        sorted[232]=206;
        sorted[233]=108;
        sorted[234]=57;
        sorted[235]=34;
        sorted[236]=1;
        sorted[237]=138;
        sorted[238]=51;
        sorted[239]=174;
        sorted[240]=315;
        sorted[241]=126;
        sorted[242]=31;
        sorted[243]=164;
        sorted[244]=76;
        sorted[245]=175;
        sorted[246]=133;
        sorted[247]=127;
        sorted[248]=80;
        sorted[249]=251;
        sorted[250]=101;
        sorted[251]=92;
        sorted[252]=220;
        sorted[253]=97;
        sorted[254]=42;
        sorted[255]=39;
        sorted[256]=22;
        sorted[257]=312;
        sorted[258]=215;
        sorted[259]=223;
        sorted[260]=182;
        sorted[261]=228;
        sorted[262]=249;
        sorted[263]=184;
        sorted[264]=217;
        sorted[265]=136;
        sorted[266]=89;
        sorted[267]=173;
        sorted[268]=225;
        sorted[269]=151;
        sorted[270]=65;
        sorted[271]=301;
        sorted[272]=304;
        sorted[273]=224;
        sorted[274]=231;
        sorted[275]=43;
        sorted[276]=321;
        sorted[277]=78;
        sorted[278]=311;
        sorted[279]=252;
        sorted[280]=254;
        sorted[281]=229;
        sorted[282]=102;
        sorted[283]=244;
        sorted[284]=247;
        sorted[285]=305;
        sorted[286]=18;
        sorted[287]=166;
        sorted[288]=54;
        sorted[289]=11;
        sorted[290]=71;
        sorted[291]=183;
        sorted[292]=268;
        sorted[293]=107;
        sorted[294]=28;
        sorted[295]=53;
        sorted[296]=302;
        sorted[297]=270;
        sorted[298]=272;
        sorted[299]=37;
        sorted[300]=35;
        sorted[301]=46;
        sorted[302]=88;
        sorted[303]=180;
        sorted[304]=273;
        sorted[305]=103;
        sorted[306]=140;
        sorted[307]=145;
        sorted[308]=159;
        sorted[309]=240;
        sorted[310]=96;
        sorted[311]=52;
        sorted[312]=202;
        sorted[313]=74;
        sorted[314]=291;
        sorted[315]=306;
        sorted[316]=274;
        sorted[317]=135;
        sorted[318]=212;
        sorted[319]=119;
        sorted[320]=219;

        for(int i =0;i<321;i++){
            if(sorted[i]==x)
                found = i;

        }
        return found+1;
    }
    private int hym(int x){
        int sorted[] = new int[321];
        sorted[0]=235;
        sorted[1]=155;
        sorted[2]=72;
        sorted[3]=258;
        sorted[4]=116;
        sorted[5]=112;
        sorted[6]=226;
        sorted[7]=238;
        sorted[8]=213;
        sorted[9]=172;
        sorted[10]=161;
        sorted[11]=109;
        sorted[12]=279;
        sorted[13]=73;
        sorted[14]=200;
        sorted[15]=50;
        sorted[16]=79;
        sorted[17]=17;
        sorted[18]=290;
        sorted[19]=160;
        sorted[20]=255;
        sorted[21]=81;
        sorted[22]=243;
        sorted[23]=169;
        sorted[24]=250;
        sorted[25]=178;
        sorted[26]=95;
        sorted[27]=139;
        sorted[28]=168;
        sorted[29]=293;
        sorted[30]=48;
        sorted[31]=113;
        sorted[32]=314;
        sorted[33]=90;
        sorted[34]=303;
        sorted[35]=181;
        sorted[36]=193;
        sorted[37]=14;
        sorted[38]=144;
        sorted[39]=261;
        sorted[40]=234;
        sorted[41]=87;
        sorted[42]=128;
        sorted[43]=27;
        sorted[44]=194;
        sorted[45]=29;
        sorted[46]=30;
        sorted[47]=118;
        sorted[48]=19;
        sorted[49]=154;
        sorted[50]=281;
        sorted[51]=300;
        sorted[52]=295;
        sorted[53]=260;
        sorted[54]=216;
        sorted[55]=125;
        sorted[56]=210;
        sorted[57]=209;
        sorted[58]=177;
        sorted[59]=284;
        sorted[60]=257;
        sorted[61]=288;
        sorted[62]=99;
        sorted[63]=318;
        sorted[64]=21;
        sorted[65]=253;
        sorted[66]=86;
        sorted[67]=7;
        sorted[68]=204;
        sorted[69]=176;
        sorted[70]=186;
        sorted[71]=313;
        sorted[72]=150;
        sorted[73]=317;
        sorted[74]=307;
        sorted[75]=275;
        sorted[76]=276;
        sorted[77]=115;
        sorted[78]=58;
        sorted[79]=197;
        sorted[80]=147;
        sorted[81]=20;
        sorted[82]=271;
        sorted[83]=241;
        sorted[84]=192;
        sorted[85]=269;
        sorted[86]=104;
        sorted[87]=45;
        sorted[88]=70;
        sorted[89]=282;
        sorted[90]=163;
        sorted[91]=248;
        sorted[92]=246;
        sorted[93]=162;
        sorted[94]=61;
        sorted[95]=233;
        sorted[96]=64;
        sorted[97]=36;
        sorted[98]=111;
        sorted[99]=4;
        sorted[100]=262;
        sorted[101]=2;
        sorted[102]=298;
        sorted[103]=170;
        sorted[104]=171;
        sorted[105]=267;
        sorted[106]=62;
        sorted[107]=148;
        sorted[108]=195;
        sorted[109]=100;
        sorted[110]=40;
        sorted[111]=237;
        sorted[112]=67;
        sorted[113]=132;
        sorted[114]=8;
        sorted[115]=23;
        sorted[116]=59;
        sorted[117]=236;
        sorted[118]=130;
        sorted[119]=239;
        sorted[120]=289;
        sorted[121]=286;
        sorted[122]=152;
        sorted[123]=285;
        sorted[124]=10;
        sorted[125]=266;
        sorted[126]=292;
        sorted[127]=120;
        sorted[128]=32;
        sorted[129]=63;
        sorted[130]=6;
        sorted[131]=201;
        sorted[132]=33;
        sorted[133]=143;
        sorted[134]=296;
        sorted[135]=191;
        sorted[136]=25;
        sorted[137]=13;
        sorted[138]=15;
        sorted[139]=207;
        sorted[140]=208;
        sorted[141]=199;
        sorted[142]=185;
        sorted[143]=56;
        sorted[144]=221;
        sorted[145]=187;
        sorted[146]=320;
        sorted[147]=299;
        sorted[148]=38;
        sorted[149]=264;
        sorted[150]=110;
        sorted[151]=259;
        sorted[152]=230;
        sorted[153]=308;
        sorted[154]=218;
        sorted[155]=265;
        sorted[156]=297;
        sorted[157]=9;
        sorted[158]=190;
        sorted[159]=245;
        sorted[160]=146;
        sorted[161]=3;
        sorted[162]=158;
        sorted[163]=91;
        sorted[164]=156;
        sorted[165]=134;
        sorted[166]=263;
        sorted[167]=214;
        sorted[168]=287;
        sorted[169]=106;
        sorted[170]=77;
        sorted[171]=83;
        sorted[172]=84;
        sorted[173]=93;
        sorted[174]=82;
        sorted[175]=129;
        sorted[176]=316;
        sorted[177]=41;
        sorted[178]=196;
        sorted[179]=85;
        sorted[180]=69;
        sorted[181]=105;
        sorted[182]=60;
        sorted[183]=94;
        sorted[184]=157;
        sorted[185]=44;
        sorted[186]=167;
        sorted[187]=75;
        sorted[188]=68;
        sorted[189]=114;
        sorted[190]=141;
        sorted[191]=153;
        sorted[192]=294;
        sorted[193]=26;
        sorted[194]=12;
        sorted[195]=319;
        sorted[196]=149;
        sorted[197]=278;
        sorted[198]=117;
        sorted[199]=188;
        sorted[200]=283;
        sorted[201]=47;
        sorted[202]=49;
        sorted[203]=24;
        sorted[204]=131;
        sorted[205]=280;
        sorted[206]=124;
        sorted[207]=277;
        sorted[208]=310;
        sorted[209]=179;
        sorted[210]=122;
        sorted[211]=16;
        sorted[212]=256;
        sorted[213]=5;
        sorted[214]=242;
        sorted[215]=55;
        sorted[216]=137;
        sorted[217]=222;
        sorted[218]=232;
        sorted[219]=211;
        sorted[220]=142;
        sorted[221]=309;
        sorted[222]=189;
        sorted[223]=198;
        sorted[224]=66;
        sorted[225]=121;
        sorted[226]=165;
        sorted[227]=203;
        sorted[228]=123;
        sorted[229]=227;
        sorted[230]=98;
        sorted[231]=205;
        sorted[232]=206;
        sorted[233]=108;
        sorted[234]=57;
        sorted[235]=34;
        sorted[236]=1;
        sorted[237]=138;
        sorted[238]=51;
        sorted[239]=174;
        sorted[240]=315;
        sorted[241]=126;
        sorted[242]=31;
        sorted[243]=164;
        sorted[244]=76;
        sorted[245]=175;
        sorted[246]=133;
        sorted[247]=127;
        sorted[248]=80;
        sorted[249]=251;
        sorted[250]=101;
        sorted[251]=92;
        sorted[252]=220;
        sorted[253]=97;
        sorted[254]=42;
        sorted[255]=39;
        sorted[256]=22;
        sorted[257]=312;
        sorted[258]=215;
        sorted[259]=223;
        sorted[260]=182;
        sorted[261]=228;
        sorted[262]=249;
        sorted[263]=184;
        sorted[264]=217;
        sorted[265]=136;
        sorted[266]=89;
        sorted[267]=173;
        sorted[268]=225;
        sorted[269]=151;
        sorted[270]=65;
        sorted[271]=301;
        sorted[272]=304;
        sorted[273]=224;
        sorted[274]=231;
        sorted[275]=43;
        sorted[276]=321;
        sorted[277]=78;
        sorted[278]=311;
        sorted[279]=252;
        sorted[280]=254;
        sorted[281]=229;
        sorted[282]=102;
        sorted[283]=244;
        sorted[284]=247;
        sorted[285]=305;
        sorted[286]=18;
        sorted[287]=166;
        sorted[288]=54;
        sorted[289]=11;
        sorted[290]=71;
        sorted[291]=183;
        sorted[292]=268;
        sorted[293]=107;
        sorted[294]=28;
        sorted[295]=53;
        sorted[296]=302;
        sorted[297]=270;
        sorted[298]=272;
        sorted[299]=37;
        sorted[300]=35;
        sorted[301]=46;
        sorted[302]=88;
        sorted[303]=180;
        sorted[304]=273;
        sorted[305]=103;
        sorted[306]=140;
        sorted[307]=145;
        sorted[308]=159;
        sorted[309]=240;
        sorted[310]=96;
        sorted[311]=52;
        sorted[312]=202;
        sorted[313]=74;
        sorted[314]=291;
        sorted[315]=306;
        sorted[316]=274;
        sorted[317]=135;
        sorted[318]=212;
        sorted[319]=119;
        sorted[320]=219;

        return sorted[x];
    }


    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int section) {
        return alphaIndexer.get(sections[section]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return positionIndexer.get(position);
    }

    public void QuickToast(String s) {
        Toast.makeText(getContext(), s,
                Toast.LENGTH_SHORT).show();
    }
}

