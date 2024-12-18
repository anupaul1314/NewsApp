package com.example.newsapp.features.auth.signup

import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.features.auth.AuthViewModal
import com.example.newsapp.features.newslist.NewsActivity
import com.example.newsapp.R

@Composable
fun SignUp(
    authViewModal: AuthViewModal,
    onButtonCLicked: () -> Unit
) {

    val context = LocalContext.current
    val email by authViewModal.email.collectAsState()
    val password by authViewModal.password.collectAsState()
    val fullName by authViewModal.fullName.collectAsState()

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFAA336A),
            Color(0xff804d9f)
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .height(600.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomStartPercent = 100,
                        bottomEndPercent = 100
                    )
                )
                .background(gradient)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.signup),
                fontSize = 40.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .fillMaxWidth(),
                value = fullName,
                onValueChange = {
                    authViewModal.updateFullName(it)
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_full_name))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = ""
                    )
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .fillMaxWidth(),
                value = email,
                onValueChange = {
                    authViewModal.updateEmail(it)
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_email))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = ""
                    )
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .fillMaxWidth(),
                value = password,
                onValueChange = {
                    authViewModal.updatePassword(it)
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_password))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = ""
                    )
                }
            )
            Spacer(modifier = Modifier.height(50.dp))
            OutlinedButton(
                modifier = Modifier
                    .height(60.dp)
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth(),
                onClick = {
                    authViewModal.createUser(
                        onSuccess =  {
                            context.startActivity(Intent(context, NewsActivity::class.java))
                        },
                        onFailure = {
                            Toast.makeText(context,"Please try again later..", Toast.LENGTH_LONG).show()
                        }
                        )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = stringResource(id = R.string.signup),
                    fontSize = 25.sp,
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    modifier = Modifier.size(35.dp),
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .clickable {
                        onButtonCLicked()
                    }
                    .fillMaxWidth(),
                text = stringResource(id = R.string.already_exist),
                fontSize = 15.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

        }

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(
                        RoundedCornerShape(
                            topStartPercent = 50,
                            topEndPercent = 100,
                            bottomEndPercent = 100
                        )
                    )
                    .background(gradient)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(
                        RoundedCornerShape(
                            topStartPercent = 100,
                            topEndPercent = 50,
                            bottomStartPercent = 100
                        )
                    )
                    .background(gradient)
            )
        }
    }
}
