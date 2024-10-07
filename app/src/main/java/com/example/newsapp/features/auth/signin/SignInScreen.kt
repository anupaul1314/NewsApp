package com.example.newsapp.features.auth.signin

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.features.newslist.NewsActivity
import com.example.newsapp.localization.LocaleHelper
import com.example.newsapp.R
import com.example.newsapp.features.auth.AuthViewModal
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SignIn(
    authViewModal: AuthViewModal,
    onButtonClicked: () -> Unit
) {
    val context = LocalContext.current
    val email by authViewModal.email.collectAsState()
    val password by authViewModal.password.collectAsState()

    var navigateToNewsActivity by remember {
        mutableStateOf(false)
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFAA336A),
            Color(0xff804d9f)
        )
    )

    // SignIn launcher to handle Google SignIn Intent
    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        authViewModal.handleSignInResult(
            task.result,
            onSuccess = {
                navigateToNewsActivity = true
            },
            onFailure = {
                Toast.makeText(context, "Google sign-in failure", Toast.LENGTH_SHORT).show()
            }
        )
    }
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
                text = "SignIn",
                fontSize = 40.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(15.dp))
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
                    .height(50.dp)
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth(),
                onClick = {
                    authViewModal.signInUser(
                        onSuccess = {
                            Toast.makeText(context, "${it?.email}", Toast.LENGTH_SHORT).show()
                            navigateToNewsActivity = true
                        },
                        onFailure = {
                            Toast.makeText(context, "Sign In Failure", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "Login",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                modifier = Modifier
                    .height(50.dp)
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth(),
                onClick = {
                    authViewModal.signInWithGoogle(
                        context,
                        onExecute = {signInIntent->
                            signInLauncher.launch(signInIntent)
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Icon(
                    modifier = Modifier.size(22.dp),
                    painter = painterResource(id = R.drawable.ic_google_logo),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Sign in with Google",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                modifier = Modifier
                    .height(50.dp)
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth(),
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "Sign in with otp",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Bold
                )
            }

            if (navigateToNewsActivity) {
                navigateToNewsActivity = false
                context.startActivity(Intent(context,NewsActivity::class.java))
            }


            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .clickable {
                        onButtonClicked()
                    }
                    .fillMaxWidth(),
                text = "New User? Sign Up",
                fontSize = 15.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                }) {
                    Text(text = "English")
                }
                Button(onClick = {
                    LocaleHelper.setLocale(context,"hi")
                    (context as? Activity)?.recreate()
                }) {
                    Text(text = "Hindi")
                }
            }
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
