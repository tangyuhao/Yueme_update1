package com.syc.yueme.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.service.ChatService;
import com.syc.yueme.service.UpdateService;
import com.syc.yueme.service.UserService;
import com.syc.yueme.ui.activity.NotifySettingActivity;
import com.syc.yueme.ui.activity.PasswordChangeActivity;
import com.syc.yueme.ui.activity.UserInfoActivity;
import com.syc.yueme.util.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lzw on 14-9-17.
 */
public class MySpaceFragment extends BaseFragment implements View.OnClickListener {
    private static final int IMAGE_PICK_REQUEST = 1;
    private static final int CROP_REQUEST = 2;
    TextView usernameView, schoolidView;
    ImageView avatarView,avatarBackgroundView;
    View myyueLayout, avatarLayout,yingyueLayout,infoLayout,
            specialyueLayout, notifyLayout, passwordchangeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_space_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        headerLayout.showTitle(R.string.me);
        findView();
        refresh();
    }

    private void refresh() {
        AVUser curUser = AVUser.getCurrentUser();
        usernameView.setText(curUser.getUsername());
        schoolidView.setText(User.getGenderDesc(curUser));
        UserService.displayAvatar(User.getAvatarUrl(curUser), avatarView);
    }

    private void findView() {
        View fragmentView = getView();
        usernameView = (TextView) fragmentView.findViewById(R.id.username);
        avatarBackgroundView = (ImageView) fragmentView.findViewById(R.id.avatarbackground);
        avatarView = (ImageView) fragmentView.findViewById(R.id.avatar);
        myyueLayout = fragmentView.findViewById(R.id.myyueLayout);
        infoLayout = fragmentView.findViewById(R.id.infoLayout);
        avatarLayout = fragmentView.findViewById(R.id.avatarLayout);
        yingyueLayout = fragmentView.findViewById(R.id.yingyueLayout);
        specialyueLayout = fragmentView.findViewById(R.id.specialyueLayout);
        notifyLayout = fragmentView.findViewById(R.id.notifyLayout);
        schoolidView = (TextView) fragmentView.findViewById(R.id.schoolnumber);
        passwordchangeLayout = fragmentView.findViewById(R.id.passwordchangeLayout);

//        avatarLayout.setOnClickListener(this);
        myyueLayout.setOnClickListener(this);
        infoLayout.setOnClickListener(this);
        yingyueLayout.setOnClickListener(this);
        specialyueLayout.setOnClickListener(this);
        passwordchangeLayout.setOnClickListener(this);
        notifyLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.infoLayout) {
            startActivity(new Intent(ctx,UserInfoActivity.class));
//            Intent intent = new Intent(Intent.ACTION_PICK, null);
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//            startActivityForResult(intent, IMAGE_PICK_REQUEST);
        } else if (id == R.id.yingyueLayout) {
            showSexChooseDialog();
//      ChatService.closeSession();
//      AVUser.logOut();
//      getActivity().finish();
        } else if (id == R.id.myyueLayout) {
            showSexChooseDialog();
        } else if (id == R.id.notifyLayout) {
            Utils.goActivity(ctx, NotifySettingActivity.class);
        } else if (id == R.id.specialyueLayout) {
            UpdateService updateService = UpdateService.getInstance(getActivity());
            updateService.showSureUpdateDialog();
        }else if (id == R.id.passwordchangeLayout){
//          Utils.goActivity(ctx, PasswordChangeActivity.class);
            startActivity(new Intent(ctx,PasswordChangeActivity.class));
        }
    }

    SaveCallback saveCallback = new SaveCallback() {
        @Override
        public void done(AVException e) {
            refresh();
        }
    };

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
                startImageCrop(uri, 200, 200, CROP_REQUEST);
            } else if (requestCode == CROP_REQUEST) {
                final String path = saveCropAvatar(data);
                new SimpleNetTask(ctx) {
                    @Override
                    protected void doInBack() throws Exception {
                        UserService.saveAvatar(path);
                    }

                    @Override
                    protected void onSucceed() {
                        refresh();
                    }
                }.execute();

            }
        }
    }

    public Uri startImageCrop(Uri uri, int outputX, int outputY,
                              int requestCode) {
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
