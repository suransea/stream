package top.srsea.stream.tool;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import top.srsea.stream.BaseActivity;
import top.srsea.stream.R;
import top.srsea.stream.util.Md5Hash;

public class Md5Activity extends BaseActivity {
    private TextInputEditText contentEdit;
    private Button computeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md5);
        setTitle(R.string.title_activity_md5);
        contentEdit = findViewById(R.id.et_md5);
        computeButton = findViewById(R.id.btn_compute);
        initView();
    }

    private void initView() {
        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEdit.getText().toString();
                if (content.isEmpty()) return;
                contentEdit.setText(Md5Hash.hash(content));
            }
        });
    }
}
