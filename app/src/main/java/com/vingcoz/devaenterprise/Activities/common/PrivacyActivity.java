package com.vingcoz.devaenterprise.Activities.common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.vingcoz.devaenterprise.R;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_activity);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Privacy Policy");
        }

        TextView txtRefundText = findViewById(R.id.txtPrivacyPolicy);

        String ForText = "<p>The returns policy brings in a lot of changes keeping in mind our loyal customer base.<br />One small caveat, all products listed under a particular category may not have the same returns policy.</p><p><strong>Consider 4 buckets to understand our return policy</strong></p><ul><li><strong>10 days</strong></li></ul><p>You may request for a replacement within 10 days of delivery for all electronics (Large appliances, mobile phones, laptops etc.) and a few verticals in Lifestyle.&nbsp;<ahref=\"https://www.flipkart.com/pages/returnpolicy\" target=\"_blank\" rel=\"noopener noreferrer\">Click here</a>&nbsp;for more details.</p><ul><li><strong>30 days</strong></li></ul><p>You may request for a refund/ replacement/ exchange within 30 days of delivery for the lifestyle categorywhich includes clothing (excluding lingerie, innerwear, socks and freebies), footwear, eyewear, fashion accessories and jewellery (non-precious).&nbsp;<ahref=\"https://www.flipkart.com/pages/returnpolicy\" target=\"_blank\" rel=\"noopener noreferrer\">Click here</a>&nbsp;for more details.</p><p><strong>NOTE:</strong>&nbsp;<em>Be aware that for the above two buckets Flipkart&rsquo;s returns policy applies as long as the items are unused,undamaged and with all original tags &amp; packaging intact.</em></p><ul><li><strong>No returns</strong></li></ul><p>Certain products cannot be returned. View this list&nbsp;<a href=\"http://www.flipkart.com/non-returnables\" target=\"_blank\" rel=\"noopener noreferrer\">here</a>.</p>";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtRefundText.setText(Html.fromHtml(ForText, Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtRefundText.setText(Html.fromHtml(ForText));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
