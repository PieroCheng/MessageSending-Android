package com.example.redfire.sendmassage;


import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;


import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity {
    private Button btn_file;
    private Button btn_send;
    private EditText et_massage;
    private TextView tv_chooseCollege;
    private static String TAG = "MainActivity";
    int row;
    PendingIntent paIntent;
    ArrayList<String> CellPhoneNumber = new ArrayList<String>();
    String pptPath;
    SmsManager smsManager;
    ArrayList<HashMap<String, String>> al;
    ListView lv;

    private Intent fileChooserIntent;

    private static final int REQUEST_CODE = 1;   //请求码
    public static final String EXTRA_FILE_CHOOSER = "file_chooser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_file = (Button) findViewById(R.id.btn_file);
        et_massage = (EditText) findViewById(R.id.et_massage);
        tv_chooseCollege = (TextView) findViewById(R.id.tv_chooseCollege);
        fileChooserIntent = new Intent(this,
                FileChooserActivity.class);

        btn_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                    startActivityForResult(fileChooserIntent, REQUEST_CODE);
                else
                    toast(getText(R.string.sdcard_unmonted_hint));
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(), 0);
                smsManager = SmsManager.getDefault();
                for (int i = 1; i < row; i++) {
                    if (CellPhoneNumber.get(i).equals("")){

                    }else {
                    String phoneNumber = CellPhoneNumber.get(i);
                    smsManager.sendTextMessage(phoneNumber, null, et_massage.getText().toString(), paIntent,
                            null);}
                }
                //  Toast.makeText(MainActivity.this,"已成功发送给"+(row-1)+"名同学！",Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "已成功发送", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toast(CharSequence hint) {
        Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.v(TAG, "onActivityResult#requestCode:" + requestCode + "#resultCode:" + resultCode);
        if (resultCode == RESULT_CANCELED) {
            toast(getText(R.string.open_file_none));
            return;
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            //获取路径名
            pptPath = data.getStringExtra(EXTRA_FILE_CHOOSER);
            Log.v(TAG, "onActivityResult # pptPath : " + pptPath);
            showChooseCollegeDialog();
        }
    }

    String[] chooseCollege = new String[]{"小营", "健翔桥"};

    private void showChooseCollegeDialog() {
        new AlertDialog.Builder(this).
                setTitle("选择校区").
                setIcon(android.R.drawable.ic_dialog_info).
                setSingleChoiceItems(chooseCollege, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                College(which);
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("取消", null)
                .show();
    }

    private void College(int which) {
        switch (which) {
            case 0:
                xiaoYing();
                break;
            case 1:
                jianXiangQiao();
        }
    }

    private void xiaoYing() {
        lv = (ListView) findViewById(R.id.listView);
        al = new ArrayList<HashMap<String, String>>();
        File file = new File(pptPath);
        Workbook wb = null;
        try {
            wb = Workbook.getWorkbook(file);
            Sheet sheet = wb.getSheet(0);
            row = sheet.getRows();
            int row = sheet.getRows();
            HashMap<String, String> hm = null;
            tv_chooseCollege.setText("小营");
            for (int i = 0; i < row; ++i) {
                Cell cellname = sheet.getCell(0, i);
                Cell cellcollege = sheet.getCell(1, i);
                Cell cellphone = sheet.getCell(14, i);
                System.out.println(cellname.getContents() + ":" + cellcollege.getContents() + ":" + cellphone.getContents());
                hm = new HashMap<String, String>();
                if (cellcollege.getContents().equals("机电工程学院")) {
                    hm.put("NAME", cellname.getContents());
                    hm.put("COLLEGE", cellcollege.getContents());
                    hm.put("PHONE", cellphone.getContents());
                    al.add(hm);
                    CellPhoneNumber.add(cellphone.getContents());
                } else if (cellcollege.getContents().equals("信息管理学院")) {
                    hm.put("NAME", cellname.getContents());
                    hm.put("COLLEGE", cellcollege.getContents());
                    hm.put("PHONE", cellphone.getContents());
                    al.add(hm);
                    CellPhoneNumber.add(cellphone.getContents());
                } else if (cellcollege.getContents().equals("理学院")) {
                    hm.put("NAME", cellname.getContents());
                    hm.put("COLLEGE", cellcollege.getContents());
                    hm.put("PHONE", cellphone.getContents());
                    al.add(hm);
                    CellPhoneNumber.add(cellphone.getContents());
                } else if (cellcollege.getContents().equals("仪器科学与光电工程学院")) {
                    hm.put("NAME", cellname.getContents());
                    hm.put("COLLEGE", cellcollege.getContents());
                    hm.put("PHONE", cellphone.getContents());
                    al.add(hm);
                    CellPhoneNumber.add(cellphone.getContents());
                } else if (cellcollege.getContents().equals("自动化学院")) {
                    hm.put("NAME", cellname.getContents());
                    hm.put("COLLEGE", cellcollege.getContents());
                    hm.put("PHONE", cellphone.getContents());
                    al.add(hm);
                    CellPhoneNumber.add(cellphone.getContents());
                } else if (cellcollege.getContents().equals("学院")) {
                    hm.put("NAME", cellname.getContents());
                    hm.put("COLLEGE", cellcollege.getContents());
                    hm.put("PHONE", cellphone.getContents());
                    al.add(hm);
                    CellPhoneNumber.add(cellphone.getContents());
                }
            }
            SimpleAdapter sa = new SimpleAdapter(this, al, R.layout.item_list, new String[]{"NAME", "COLLEGE", "PHONE"}, new int[]{R.id.tv_name, R.id.tv_college, R.id.tv_phone});
            lv.setAdapter(sa);
            // Toast.makeText(MainActivity.this,"已选中"+(row-1)+"名同学",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    private void jianXiangQiao() {
        lv = (ListView) findViewById(R.id.listView);
        al = new ArrayList<HashMap<String, String>>();
        File file = new File(pptPath);
        Workbook wb = null;
        try {
            wb = Workbook.getWorkbook(file);
            Sheet sheet = wb.getSheet(0);
            row = sheet.getRows();
            int row = sheet.getRows();
            HashMap<String, String> hm = null;
            tv_chooseCollege.setText("健翔桥");
            for (int i = 0; i < row; ++i) {
                Cell cellname = sheet.getCell(0, i);
                Cell cellcollege = sheet.getCell(1, i);
                Cell cellphone = sheet.getCell(14, i);
                System.out.println(cellname.getContents() + ":" + cellcollege.getContents() + ":" + cellphone.getContents());
                hm = new HashMap<String, String>();
                if (cellcollege.getContents().equals("信息与通信工程学院")) {
                    hm.put("NAME", cellname.getContents());
                    hm.put("COLLEGE", cellcollege.getContents());
                    hm.put("PHONE", cellphone.getContents());
                    al.add(hm);
                    CellPhoneNumber.add(cellphone.getContents());
                } else if (cellcollege.getContents().equals("计算机学院")) {
                    hm.put("NAME", cellname.getContents());
                    hm.put("COLLEGE", cellcollege.getContents());
                    hm.put("PHONE", cellphone.getContents());
                    al.add(hm);
                    CellPhoneNumber.add(cellphone.getContents());
                } else if (cellcollege.getContents().equals("学院")) {
                    hm.put("NAME", cellname.getContents());
                    hm.put("COLLEGE", cellcollege.getContents());
                    hm.put("PHONE", cellphone.getContents());
                    al.add(hm);
                    CellPhoneNumber.add(cellphone.getContents());
                }
            }
            SimpleAdapter sa = new SimpleAdapter(this, al, R.layout.item_list, new String[]{"NAME", "COLLEGE", "PHONE"}, new int[]{R.id.tv_name, R.id.tv_college, R.id.tv_phone});
            lv.setAdapter(sa);
            // Toast.makeText(MainActivity.this,"已选中"+(row-1)+"名同学",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

}



