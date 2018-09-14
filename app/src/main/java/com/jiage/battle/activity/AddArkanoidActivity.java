package com.jiage.battle.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jiage.battle.R;
import com.jiage.battle.adapter.AddArkanoidAdapter;
import com.jiage.battle.dao.JsonDbModelDao;
import com.jiage.battle.entity.CheckpointEntity;
import com.jiage.battle.entity.CheckpointItemEntity;
import com.jiage.battle.entity.CustomizeEntity;
import com.jiage.battle.surface.arkanoid.ArkanoidZDYSurface;
import com.jiage.battle.surface.arkanoid.CustomizeBlock;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/8/21
 * 说明：添加打方块自定义关卡
 */

public class AddArkanoidActivity extends BaseActivit implements AdapterView.OnItemSelectedListener {
    @ViewInject(R.id.act_customize_spinner)
    private Spinner spinner;
    @ViewInject(R.id.act_customize_lie)
    private EditText lie;
    @ViewInject(R.id.act_customize_hang)
    private EditText hang;
    @ViewInject(R.id.act_customize_gao)
    private EditText gao;
    @ViewInject(R.id.act_customize_surface)
    private ArkanoidZDYSurface surface;


    private List<CustomizeEntity> listModel = new ArrayList<>();


    @Override
    public int bindLayout() {
        return R.layout.act_addarkanoid;
    }

    @Override
    public void initView(View view) {
        setSwipeBackEnable(false);
        mTitle.setCenterText("编辑关卡");
        mTitle.setBackText("保存");
        mTitle.setRightText("开始");
    }

    @Override
    public void onTitleBackListener() {
        Vector<CustomizeBlock> blockList = surface.getVcBlock();
        if (blockList.size() < 1)
            Toast.makeText(this, "未绘制关卡", Toast.LENGTH_LONG).show();
        int[][] checkpoints = new int[hang.getText().toString().equals("") ? 1 : Integer.parseInt(hang.getText().toString())]
                [lie.getText().toString().equals("") ? 1 : Integer.parseInt(lie.getText().toString())];
        int a = 0;
        int b = 0;
        for (int i = 0; i < blockList.size(); i++) {
            CustomizeBlock block = blockList.elementAt(i);
            int zhuan = blockList.size() / (lie.getText().toString().equals("") ? 1 : Integer.parseInt(lie.getText().toString()));
            if (blockList.size() % (lie.getText().toString().equals("") ? 1 : Integer.parseInt(lie.getText().toString())) != 0) {
                zhuan++;
            }
            checkpoints[a][b] = block.getmI();
            b++;
            if (b > zhuan - 1) {
                a++;
                b = 0;
            }
        }
        CheckpointEntity entity = JsonDbModelDao.getInstance().query(CheckpointEntity.class);
        if (entity == null)
            entity = new CheckpointEntity();
        CheckpointItemEntity itemEntity = new CheckpointItemEntity();
        itemEntity.setCheckpoint(checkpoints);
        itemEntity.setHeght(Integer.parseInt(gao.getText().toString()));
        itemEntity.setScreenH(surface.getScreenH());
        itemEntity.setLie(Integer.parseInt(lie.getText().toString()));
        itemEntity.setHang(Integer.parseInt(hang.getText().toString()));
        if (entity.getItemEntityList() == null)
            entity.setItemEntityList(new ArrayList<CheckpointItemEntity>());
        entity.getItemEntityList().add(itemEntity);
        JsonDbModelDao.getInstance().insertOrUpdate(entity);
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        super.onTitleBackListener();
    }

    @Override
    public void onTitleRightListener() {
        surface.addCheckpoint(new int[hang.getText().toString().equals("") ? 1 : Integer.parseInt(hang.getText().toString())]
                [lie.getText().toString().equals("") ? 1 : Integer.parseInt(lie.getText().toString())],
                gao.getText().toString().equals("") ? 20 : Integer.parseInt(gao.getText().toString()));
    }

    @Override
    public void doView() {
        for (int i = -1; i < 8; i++) {
            CustomizeEntity entity = new CustomizeEntity();
            entity.setCode(i);
            switch (i) {
                case -1:
                    entity.setText("钢板");
                    entity.setColor(Color.BLACK);
                    break;
                case 0:
                    entity.setText("清除");
                    entity.setColor(Color.WHITE);
                    break;
                case 1:
                    entity.setText("1血");
                    entity.setColor(Color.BLUE);
                    break;
                case 2:
                    entity.setText("2血");
                    entity.setColor(Color.GREEN);
                    break;
                case 3:
                    entity.setText("3血");
                    entity.setColor(Color.YELLOW);
                    break;
                case 4:
                    entity.setText("4血");
                    entity.setColor(Color.RED);
                    break;
                case 5:
                    entity.setText("5血");
                    entity.setColor(Color.CYAN);
                    break;
                case 6:
                    entity.setText("6血");
                    entity.setColor(Color.DKGRAY);
                    break;
                case 7:
                    entity.setText("7血");
                    entity.setColor(Color.GRAY);
                    break;
            }
            listModel.add(entity);
        }


        AddArkanoidAdapter adapter = new AddArkanoidAdapter(listModel, this);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        surface.setCode(listModel.get(position).getCode());
        surface.setColor(listModel.get(position).getColor());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
