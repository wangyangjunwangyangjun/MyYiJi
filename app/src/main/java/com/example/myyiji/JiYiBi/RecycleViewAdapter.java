package com.example.myyiji.JiYiBi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myyiji.DataBaseItem;
import com.example.myyiji.GridImageAdapter;
import com.example.myyiji.MyGridView;
import com.example.myyiji.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.myyiji.ListView_ShouRu.M_RecycleViewAdapter.dataformat;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private DataBaseItem baseItem = new DataBaseItem();
    private int count = 0;
    private Uri imageUri;
    public RecycleViewAdapter(Context context) {
        this.context = context;
    }
    public DataBaseItem getBaseItem() {
        return baseItem;
    }
    private enum ITEM_TYPE{
        firstfloor,
        secondfloor,
        thirdfloor,
        fouthfloor,
        fifthfloor,
        sixthfloor,
        seventhfloor
    }
    @Override
    public int getItemViewType(int position) {
        return JudgeType(position);
    }
    public int JudgeType(int position) {
        if (position == 0) {
            return ITEM_TYPE.firstfloor.ordinal();
        } else if (position == 1) {
            return ITEM_TYPE.secondfloor.ordinal();
        } else if (position == 2) {
            return ITEM_TYPE.thirdfloor.ordinal();
        } else if (position == 3) {
            return ITEM_TYPE.fouthfloor.ordinal();
        } else if (position == 4) {
            return ITEM_TYPE.fifthfloor.ordinal();
        } else if (position == 5) {
            return ITEM_TYPE.sixthfloor.ordinal();
        } else{
            return ITEM_TYPE.seventhfloor.ordinal();
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_TYPE.firstfloor.ordinal()){
            return new firstfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item1, parent, false));
        }else if(viewType==ITEM_TYPE.secondfloor.ordinal()){
            return new secondfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item2, parent, false));
        }else if(viewType==ITEM_TYPE.thirdfloor.ordinal()){
            return new thirdfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item3, parent, false));
        }else if(viewType==ITEM_TYPE.fouthfloor.ordinal()){
            return new fouthfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item4, parent, false));
        } else if(viewType==ITEM_TYPE.fifthfloor.ordinal()){
            return new fifthfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item5, parent, false));
        }else if(viewType==ITEM_TYPE.sixthfloor.ordinal()){
            return new sixthfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item6, parent, false));
        }else if(viewType==ITEM_TYPE.seventhfloor.ordinal()){
            return new sevenfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item7, parent, false));
        }else{
            return null;
        }
    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof firstfloorViewHolder) {
            ((firstfloorViewHolder) holder).image.setImageResource(R.drawable.wenhao);
            ((firstfloorViewHolder) holder).leibie.setText(R.string.leibie);
            baseItem.setCost(0.00);
            baseItem.setName("  work_input_"+count);
            count++;
            ((firstfloorViewHolder) holder).number.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    baseItem.setCost(Double.valueOf(((firstfloorViewHolder) holder).number.getText().toString()));
                }
            });
            ((firstfloorViewHolder) holder).name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    baseItem.setName(((firstfloorViewHolder) holder).name.getText().toString());
                }
            });

        }else if(holder instanceof secondfloorViewHolder) {
            ((secondfloorViewHolder) holder).b1.setText(R.string.ZhiChu);
            ((secondfloorViewHolder) holder).b2.setText(R.string.ShouRu);
        }else if(holder instanceof thirdfloorViewHolder) {
            final int[] listicon = {R.drawable.p01,R.drawable.p02,R.drawable.p03,R.drawable.p04,R.drawable.p05,R.drawable.p06,R.drawable.p09,R.drawable.p010,R.drawable.p012,R.drawable.p014,};;
            final String[] list_English_name = {"work_input","interest","award","parents","lucky","company","medical_aid","red_package","others","unknown"};
            final String[] listname = {"工作收入","存款利息","业绩奖金","长辈给予","彩票收入","公司福利","医疗补助","微信红包","其他收入","收入不详"};
            ((thirdfloorViewHolder) holder).icon_group.setNumColumns(5);
            ((thirdfloorViewHolder) holder).icon_group.setAdapter(new GridImageAdapter(context,listicon,listname));
            ((thirdfloorViewHolder) holder).icon_group.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
            ((thirdfloorViewHolder) holder).icon_group.setItemChecked(0,true);
            ((thirdfloorViewHolder) holder).icon_group.setSelection(0);
            ((thirdfloorViewHolder) holder).icon_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
                    view.setSelected(true);
                    baseItem.setCosticon(listicon[position1]);
                    baseItem.setType(list_English_name[position1]);
                }
            });
        }else if(holder instanceof fouthfloorViewHolder) {
            ((fouthfloorViewHolder) holder).datavalue.setText("请选择日期");
            ((fouthfloorViewHolder) holder).timevalue.setText("请选择时间");
            baseItem.setCostdate("未知");
            baseItem.setTime("未知");
            ((fouthfloorViewHolder) holder).dataicon.setImageResource(R.drawable.timeclock);
            ((fouthfloorViewHolder) holder).datavalue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(context,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    ((fouthfloorViewHolder) holder).datavalue.setText(year + "." + dataformat(monthOfYear) + "." + dataformat(dayOfMonth));
                                    baseItem.setCostdate(year + "." + dataformat(monthOfYear) + "." + dataformat(dayOfMonth));
                                }
                            },c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            ((fouthfloorViewHolder) holder).timevalue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar c = Calendar.getInstance();
                    new TimePickerDialog(context,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    ((fouthfloorViewHolder) holder).timevalue.setText(hourOfDay + ":" + dataformat(minute));
                                    baseItem.setTime(hourOfDay + ":" + minute);
                                }
                            },c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
                }
            });

        }else if(holder instanceof fifthfloorViewHolder) {
            ((fifthfloorViewHolder) holder).dingwei.setImageResource(R.drawable.dingwei);
            baseItem.setPlace("未知");
            ((fifthfloorViewHolder) holder).place.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    baseItem.setPlace(((fifthfloorViewHolder) holder).place.getText().toString());
                }
            });
        }else if(holder instanceof sixthfloorViewHolder) {
            ((sixthfloorViewHolder) holder).sisflooricon.setImageResource(R.drawable.beizhu);
            baseItem.setNote("无");
            ((sixthfloorViewHolder) holder).beizhu.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    baseItem.setNote(((sixthfloorViewHolder) holder).beizhu.getText().toString());
                }
            });
        }else{
            ((sevenfloorViewHolder) holder).sevenflooricon.setImageResource(R.drawable.camera);
            ((sevenfloorViewHolder) holder).shangchuntupian.setText(R.string.shangchuantupianhuopaishezhaopian);
            ((sevenfloorViewHolder) holder).shangchuntupian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File outputImage = new File(context.getExternalCacheDir(),"output_image.jpg");
                    try{
                        if(outputImage.exists()){
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(Build.VERSION.SDK_INT>=24){
                        imageUri = FileProvider.getUriForFile(context,"com.example.myyiji.JiYiBi",outputImage);
                    }else{
                        imageUri = Uri.fromFile(outputImage);
                    }
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    context.startActivity(intent);
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri));
//                        ((sevenfloorViewHolder) holder).imageView.setImageBitmap(bitmap);
                        ((sevenfloorViewHolder) holder).imageView.setImageResource(R.drawable.p55);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            baseItem.setImage(R.drawable.p55);
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
    public static class firstfloorViewHolder extends RecyclerView.ViewHolder {
        TextView leibie;
        CircleImageView image;
        EditText number;
        EditText name;
        firstfloorViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.data_name);
            leibie = view.findViewById(R.id.leibie);
            image = view.findViewById(R.id.firstfloorlogo);
            number = view.findViewById(R.id.number);
        }
    }
    public static class secondfloorViewHolder extends RecyclerView.ViewHolder {
        Button b1;
        Button b2;
        secondfloorViewHolder(@NonNull View view) {
            super(view);
            b1 = view.findViewById(R.id.jiyibi_zhichu);
            b2 = view.findViewById(R.id.jiyibi_shouru);
        }
    }
    public static class thirdfloorViewHolder extends RecyclerView.ViewHolder {
        MyGridView icon_group;
        thirdfloorViewHolder(@NonNull View view) {
            super(view);
            icon_group = view.findViewById(R.id.icon_group);
        }
    }
    public static class fouthfloorViewHolder extends RecyclerView.ViewHolder {
        ImageView dataicon;
        TextView datavalue;
        TextView timevalue;
        fouthfloorViewHolder(@NonNull View view) {
            super(view);
            dataicon = view.findViewById(R.id.dataicon);
            datavalue = view.findViewById(R.id.datavalue);
            timevalue = view.findViewById(R.id.timevalue);
        }
    }

    public static class fifthfloorViewHolder extends RecyclerView.ViewHolder {
        ImageView dingwei;
        EditText place;
        fifthfloorViewHolder(@NonNull View view) {
            super(view);
            dingwei = view.findViewById(R.id.dingwei);
            place = view.findViewById(R.id.item_place);
        }
    }
    public static class sixthfloorViewHolder extends RecyclerView.ViewHolder {
        ImageView sisflooricon;
        EditText beizhu;
        sixthfloorViewHolder(@NonNull View view) {
            super(view);
            sisflooricon = view.findViewById(R.id.sisflooricon);
            beizhu = view.findViewById(R.id.beizhu);
        }
    }
    public static class sevenfloorViewHolder extends RecyclerView.ViewHolder {
        ImageView sevenflooricon;
        TextView shangchuntupian;
        ImageView imageView;
        sevenfloorViewHolder(@NonNull View view) {
            super(view);
            sevenflooricon =  view.findViewById(R.id.sevenflooricon);
            shangchuntupian = view.findViewById(R.id.shangchuntupian);
            imageView = view.findViewById(R.id.myPhoto);
        }
    }
}
