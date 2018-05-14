package com.my51c.see51.yzxvoip.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my51c.see51.yzxvoip.AudioConverseActivity;
import com.synertone.netAssistant.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class TelListAdapter extends BaseAdapter {
    private static final String TAG = "TelListAdapter";
    private List<TelListsInfo> telListsInfo;
    private LayoutInflater mInflater;
    private Context mContext;
    private AlertDialog dialog;// �ö��Ի���
    private OnTelListListener onTelListListener;

    public TelListAdapter(List<TelListsInfo> telLists, Context context) {
        telListsInfo = telLists;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setOnTelListListener(OnTelListListener onTelListListener) {
        this.onTelListListener = onTelListListener;
    }

    public void notifyDataSetChanged(List<TelListsInfo> telLists) {
        telListsInfo = telLists;
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return telListsInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return telListsInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final TelListsInfo telInfo = telListsInfo.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.tel_list_adapter, null);
            viewHolder.rl_tel_list = (RelativeLayout) convertView.findViewById(R.id.rl_tel_list);
            viewHolder.iv_tel_head = (ImageView) convertView.findViewById(R.id.iv_tel_head);
            viewHolder.tv_tel_name = (TextView) convertView.findViewById(R.id.tv_tel_name);
            viewHolder.iv_tel_dial = (ImageView) convertView.findViewById(R.id.iv_tel_dial);
            viewHolder.tv_tel_mode = (TextView) convertView.findViewById(R.id.tv_tel_mode);
            viewHolder.tv_tel_time = (TextView) convertView.findViewById(R.id.tv_tel_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (!TextUtils.isEmpty(telInfo.getGravator())) {
            // ͷ��
        }
        if (!TextUtils.isEmpty(telInfo.getName())) {
            // �ǳ�
            viewHolder.tv_tel_name.setText(telInfo.getName());
        }

        if (telInfo.getDialFlag() == 0) { //����
            viewHolder.iv_tel_dial.setBackgroundResource(R.drawable.in_call);
            viewHolder.tv_tel_name.setTextColor(mContext.getResources().getColor(R.color.tv_tel_name));
        } else if (telInfo.getDialFlag() == 1) { //����
            viewHolder.iv_tel_dial.setBackgroundResource(R.drawable.out_call);
            viewHolder.tv_tel_name.setTextColor(mContext.getResources().getColor(R.color.tv_tel_name));
        } else if (telInfo.getDialFlag() == 2) { // ����δ��
            viewHolder.iv_tel_dial.setBackgroundResource(R.drawable.in_mis_call);
            viewHolder.tv_tel_name.setTextColor(mContext.getResources().getColor(R.color.tv_mis_dial));
        } else if (telInfo.getDialFlag() == 3) { // ����δ��
            viewHolder.iv_tel_dial.setBackgroundResource(R.drawable.out_call);
            viewHolder.tv_tel_name.setTextColor(mContext.getResources().getColor(R.color.tv_tel_name));
        }

        // 电话聊天方式
        /*if(telInfo.getTelMode() == 1) {
			viewHolder.tv_tel_mode.setText("语音通话");
		} else if(telInfo.getTelMode() == 2){
			viewHolder.tv_tel_mode.setText("语音通话");
		}*/
        if (!TextUtils.isEmpty(telInfo.getTelAdress())) {
            // �ǳ�
            viewHolder.tv_tel_mode.setText(telInfo.getTelAdress());
        }
        // 时间
        if (!TextUtils.isEmpty(telInfo.getTime())) {
            String time = telInfo.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd-HH:mm");
            String currentTime = dateFormat.format(new Date());
            if (currentTime.split("-")[0].equals(time.split("-")[0])) { // ͬһ��
                viewHolder.tv_tel_time.setText(time.split("-")[1]);
            } else {
                String dateTime = time.split("-")[0].split(":")[0] + "-" + time.split("-")[0].split(":")[1] + "-" + time.split("-")[0].split(":")[2];
                viewHolder.tv_tel_time.setText(dateTime);
            }
//				long callTime = dateFormat.parse(time).getTime();
//				long currentTime = System.currentTimeMillis();
//				if(currentTime > callTime && (currentTime - callTime)/(1000*60*60*24) < 1) {
//					viewHolder.tv_tel_time.setText(time.substring(11, 16));
//				} else {
//					viewHolder.tv_tel_time.setText(time.split(":")[0] + "-" + time.split(":")[1] + "-" + time.split(":")[2]);
//				}
        }
        viewHolder.rl_tel_list.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v != null) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //���µ���ɫ
                            v.setBackgroundColor(mContext.getResources().getColor(R.color.info_show_press));
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            //�Ƿ��ö���ɫ�仯
                            if (telInfo.getIsTop() == 1) {
                                v.setBackgroundColor(Color.parseColor("#f0f0f0"));
                            } else {
                                v.setBackgroundColor(Color.parseColor("#ffffff"));
                            }
                        default:
                            break;
                    }
                }
                return false;
            }
        });
		/*viewHolder.rl_tel_list.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, TelUserInfoActivity.class);
				intent.putExtra("userName", telInfo.getName());
				intent.putExtra("userHead", telInfo.getGravator());
				intent.putExtra("userPhone", telInfo.getTelephone());
				mContext.startActivity(intent);
			}
		});*/
        //�Ƿ��ö���ɫ�仯
        if (telInfo.getIsTop() == 1) {
            viewHolder.rl_tel_list.setBackgroundColor(Color.parseColor("#f0f0f0"));
        } else {
            viewHolder.rl_tel_list.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        viewHolder.rl_tel_list.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = telInfo.getName();
                Intent intentVoice = new Intent(mContext, AudioConverseActivity.class);
                intentVoice.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intentVoice.putExtra("phoneNumber", phone);
                intentVoice.putExtra("call_phone", phone);
                intentVoice.putExtra("call_type", 2);//直拨电话
                mContext.startActivity(intentVoice);
            }
        });
        // ���ó����������Ƿ��ö�
	/*	viewHolder.rl_tel_list.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				final TelListsInfo info = telListsInfo.get(position);
				dialog = new AlertDialog.Builder(mContext).create();
				dialog.show();
				dialog.setCanceledOnTouchOutside(true);
				dialog.getWindow().setContentView(R.layout.dialog_base1);
				TextView tv1 = (TextView) dialog.getWindow().findViewById(
						R.id.dialog_tv1);
				TextView tv2 = (TextView) dialog.getWindow().findViewById(
						R.id.dialog_tv2);
				final int isTop = info.getIsTop();
				if (isTop == 1) {
					tv1.setText("ȡ���ö�");
				} else {
					tv1.setText("�ö�����");
				}
				tv1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						info.setIsTop(isTop == 0 ? 1 : 0);
						String loginPhone = mContext.getSharedPreferences("YZX_DEMO", 1)
								.getString("YZX_ACCOUNT_INDEX", "");
						TelListsInfoDBManager.getInstance().update(info);
						List<TelListsInfo> listsData = TelListsInfoDBManager.getInstance()
								.getAll(loginPhone);
						telListsInfo.clear();
						telListsInfo.addAll(listsData);
						notifyDataSetChanged();
						if(onTelListListener != null){
							onTelListListener.onUpdate();
						}
						dialog.dismiss();
					}
				});
				tv2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						//ɾ����Ϣ�Ự
						int deleteTelListInfo = TelListsInfoDBManager.getInstance().deleteTelListInfo(info);
						Log.i(TAG, "deleteTelListInfo size = "+deleteTelListInfo);
						//ɾ����Ϣ�б�
						int deleteTelUserInfo = TelUserInfoDBManager.getInstance().deleteById(info.getTelephone());
						Log.i(TAG, "deleteTelUserInfo size = "+deleteTelUserInfo);
						synchronized (telListsInfo) {
							telListsInfo.remove(position);
						}
						notifyDataSetChanged();
						if(onTelListListener != null){
							onTelListListener.onUpdate();
						}
						dialog.dismiss();
					}
				});
				return false;
			}
		});*/
        //���Ӱ�����ɫ�仯Ч��
        return convertView;
    }

    //telListsInfo�����������
    public interface OnTelListListener {
        void onUpdate();
    }

    private class ViewHolder {
        RelativeLayout rl_tel_list;
        ImageView iv_tel_head;
        TextView tv_tel_name;
        ImageView iv_tel_dial;
        TextView tv_tel_mode;
        TextView tv_tel_time;
    }
}
  
