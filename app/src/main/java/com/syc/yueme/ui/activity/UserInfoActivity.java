package com.syc.yueme.ui.activity;

/**
 * Created by lx on 2015/2/17.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.base.App;
import com.syc.yueme.service.UserService;
import com.syc.yueme.ui.view.HeaderLayout;
import com.syc.yueme.util.Logger;
import com.syc.yueme.util.PathUtils;
import com.syc.yueme.util.PhotoUtils;
import com.syc.yueme.util.SimpleNetTask;
import com.syc.yueme.util.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
//public class UserInfoActivity extends BaseEntryActivity {
public class UserInfoActivity extends BaseEntryActivity implements OnClickListener {
    private static final int IMAGE_PICK_REQUEST = 1;
    private static final int CROP_REQUEST = 2;
    static ImageView avatarView;
    static TextView usernameView, genderView, stuidView, birthdayView,
            peoplesView, hometownView, YPAView, schoolView, nicknameView,
            characristicsView, hobbyView, signView, specialityView;
    View    usernameLayout, avatarLayout, stuidLayout, birthdayLayout,specialityLayout,
            peoplesLayout, YPALayout, genderLayout, hometownLayout,
            characristicsLayout, hobbyLayout, signLayout, nicknameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);

        findView();
        setView();
    }

    private void findView() {
        initActionBar(App.ctx.getString(R.string.userInfo));

        usernameView = (TextView) findViewById(R.id.myusername);
        avatarView = (ImageView) findViewById(R.id.myavatar);
        genderView = (TextView) findViewById(R.id.sex);
        stuidView = (TextView) findViewById(R.id.stuId);
        nicknameView = (TextView) findViewById(R.id.nickname);
//        birthdayView = (TextView) findViewById(R.id.birthday);
//        peoplesView = (TextView) findViewById(R.id.peoples);
        hometownView = (TextView) findViewById(R.id.hometown);
        YPAView = (TextView) findViewById(R.id.YPA);
        signView = (TextView) findViewById(R.id.sign);
        schoolView = (TextView) findViewById(R.id.school);
        characristicsView = (TextView) findViewById(R.id.characristics);
        hobbyView = (TextView) findViewById(R.id.hobbies);
        specialityView = (TextView) findViewById(R.id.speciality);

        usernameLayout = findViewById(R.id.usernameLayout);
        avatarLayout = findViewById(R.id.avatarLayout);
        genderLayout = findViewById(R.id.sexLayout);
//        birthdayLayout = findViewById(R.id.birthdayLayout);
        nicknameLayout = findViewById(R.id.nicknameLayout);
//        peoplesLayout = findViewById(R.id.peoplesLayout);
        hometownLayout = findViewById(R.id.hometownLayout);
        characristicsLayout=findViewById(R.id.characristicsLayout);
        hobbyLayout=findViewById(R.id.hobbiesLayout);
        signLayout=findViewById(R.id.signLayout);
        specialityLayout=findViewById(R.id.specialityLayout);


        avatarLayout.setOnClickListener(this);
        genderLayout.setOnClickListener(this);
//        birthdayLayout.setOnClickListener(this);
        nicknameLayout.setOnClickListener(this);
        hometownLayout.setOnClickListener(this);
//        peoplesLayout.setOnClickListener(this);
        specialityLayout.setOnClickListener(this);
        signLayout.setOnClickListener(this);
        hobbyLayout.setOnClickListener(this);
        characristicsLayout.setOnClickListener(this);
    }

    private void setView() {
        AVUser curUser = AVUser.getCurrentUser();
        usernameView.setText(curUser.getUsername());
        nicknameView.setText(User.getNickname(curUser));
        genderView.setText(User.getGenderDesc(curUser));
//        birthdayView.setText(User.getBirthday(curUser));
//        peoplesView.setText(User.getPeoples(curUser));
        hometownView.setText(User.getHometown(curUser));
        YPAView.setText(User.getYPA(curUser));
        signView.setText(User.getSign(curUser));
        characristicsView.setText(User.getCharacristics(curUser));
        stuidView.setText(User.getStuId(curUser));
        specialityView.setText(User.getSpeciality(curUser));
        hobbyView.setText(User.getHobbies(curUser));
        schoolView.setText(User.getSchool(curUser));
        UserService.displayAvatar(User.getAvatarUrl(curUser), avatarView);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.avatarLayout) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, IMAGE_PICK_REQUEST);
        } else if (id == R.id.sexLayout) {
            showSexChooseDialog();
//        } else if (id == R.id.birthdayLayout) {
//            showBirthdayChooseDialog();
        } else if (id == R.id.nicknameLayout) {
//            showNicknameDialog();
            Utils.goActivity(ctx,UserNicknameActivity.class);
//        } else if (id == R.id.peoplesLayout) {
//            showPeoplesDialog();
        } else if (id == R.id.hometownLayout) {
//            showHometownDialog();
            Utils.goActivity(ctx,UserHometownActivity.class);
        }else if (id == R.id.characristicsLayout) {
            startActivity(new Intent(ctx, UserCharacristicsActivity.class));
        }else if (id == R.id.hobbiesLayout) {
            startActivity(new Intent(ctx, UserHobbiesActivity.class));
        } else if (id == R.id.signLayout) {
            startActivity(new Intent(ctx, UserSignActivity.class));
        }else if (id == R.id.specialityLayout) {
            startActivity(new Intent(ctx, UserSpecialityActivity.class));
        }

    }

    SaveCallback saveCallback = new SaveCallback() {
        @Override
        public void done(AVException e) {
            setView();
        }
    };

//    public Dialog showNicknameDialog() {
//        final EditText Nick = new EditText(this);
//        return new AlertDialog.Builder(this)
//                .setTitle("请输入昵称")
//                .setView(Nick)
//                .setPositiveButton("确定",
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
////                                String nickname = texta.getText();
//                                String nickname = Nick.getText().toString();
//                                AVUser user = AVUser.getCurrentUser();
//                                User.setNickname(user, nickname);
//                                user.saveInBackground();
//                                nicknameView.setText(nickname);
//                                dialog.dismiss();
//                                Toast.makeText(getBaseContext(), "设置成功", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                )
//                .setNegativeButton("取消",
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(getBaseContext(), "设置取消", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ).show();
//    }
//    public Dialog showHometownDialog() {
//        final EditText Nick = new EditText(this);
//        return new AlertDialog.Builder(this)
//                .setTitle("请输入地区")
//                .setView(Nick)
//                .setPositiveButton("确定",
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
////                                String nickname = texta.getText();
//                                String hometown = Nick.getText().toString();
//                                AVUser user = AVUser.getCurrentUser();
//                                User.setHometown(user, hometown);
//                                user.saveInBackground();
//                                hometownView.setText(hometown);
//                                dialog.dismiss();
//                                Toast.makeText(getBaseContext(), "设置成功", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                )
//                .setNegativeButton("取消",
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(getBaseContext(), "设置取消", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ).show();
//    }
//    public Dialog showPeoplesDialog() {
//        final EditText Nick = new EditText(this);
//        return new AlertDialog.Builder(this)
//                .setTitle("请输入民族")
//                .setView(Nick)
//                .setPositiveButton("确定",
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
////                                String nickname = texta.getText();
//                                String peoples = Nick.getText().toString();
//                                AVUser user = AVUser.getCurrentUser();
//                                User.setPeoples(user, peoples);
//                                user.saveInBackground();
//                                peoplesView.setText(peoples);
//                                dialog.dismiss();
//                                Toast.makeText(getBaseContext(), "设置成功", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                )
//                .setNegativeButton("取消",
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(getBaseContext(), "设置取消", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ).show();
//    }
//    public void showBirthdayChooseDialog(){
//        DatePickerDialog datePicker = new DatePickerDialog(UserInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
//                // TODO Auto-generated method stub
//                AVUser user = AVUser.getCurrentUser();
//                String birth =year + "-" + (monthOfYear + 1) + "-" + dayOfMonth ;
//                User.setBirthday(user,birth);
//                user.saveInBackground();
//                Toast.makeText(UserInfoActivity.this, year + "year " + (monthOfYear + 1) + "month " + dayOfMonth + "day", Toast.LENGTH_SHORT).show();
//            }
//        }, 2000, 1, 1);
//        datePicker.show();
//    }
    private void showSexChooseDialog() {
        AVUser user = AVUser.getCurrentUser();
        int checkItem = User.getGender(user) == User.Gender.Male ? 0 : 1;
        new AlertDialog.Builder(ctx).setTitle(R.string.sex)
                .setSingleChoiceItems(User.genderStrings, checkItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User.Gender gender;
                        if (which == 0) {
                            gender = User.Gender.Male;
                        } else {
                            gender = User.Gender.Female;
                        }
                        UserService.saveSex(gender, saveCallback);
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.cancel, null).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.d("on Activity result " + requestCode + " " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_PICK_REQUEST) {
                Uri uri = data.getData();
                startImageCrop(uri, 75, 75, CROP_REQUEST);
            } else if (requestCode == CROP_REQUEST) {
                final String path = saveCropAvatar(data);
                new SimpleNetTask(ctx) {
                    @Override
                    protected void doInBack() throws Exception {
                        UserService.saveAvatar(path);
                    }

                    @Override
                    protected void onSucceed() {
                        setView();
                    }
                }.execute();

            }
        }
    }

    public Uri startImageCrop(Uri uri, int outputX, int outputY,int requestCode) {
        Intent intent = null;
        intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        String outputPath = PathUtils.getAvatarTmpPath();
        Uri outputUri = Uri.fromFile(new File(outputPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", false); // face detection
        startActivityForResult(intent, requestCode);
        return outputUri;
    }

    private String saveCropAvatar(Intent data) {
        Bundle extras = data.getExtras();
        String path = null;
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            if (bitmap != null) {
                bitmap = PhotoUtils.toRoundCorner(bitmap, 10);
                String filename = new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date());
                path = PathUtils.getAvatarDir() + filename;
                Logger.d("save bitmap to " + path);
                PhotoUtils.saveBitmap(PathUtils.getAvatarDir(), filename,
                        bitmap, true);
                if (bitmap != null && bitmap.isRecycled() == false) {
                    //bitmap.recycle();
                }
            }
        }
        return path;
    }
}

