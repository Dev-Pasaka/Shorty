package com.example.domain.repository

import com.example.data.remote.dto.emailDto.EmailDto
import com.example.data.remote.model.sendEmailBody.SendEmailBody

interface SendEmailsRepository {

    suspend fun sendOnboardingOtp(recipientEmail:String,recipientName:String, otpCode:String): EmailDto
    suspend fun sendOtp(recipientEmail:String,recipientName:String, otpCode:String, subject:String): EmailDto

    fun onBoardingOtpEmailTemplate(recipientName:String, otpCode:String) =
        """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Account Registration Verification</title>
                <style>
                    body {
                        font-family: 'Helvetica Neue', Arial, sans-serif;
                        background-color: #f4f4f4;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 30px auto;
                        background-color: #ffffff;
                        padding: 30px;
                        border-radius: 10px;
                        box-shadow: 0 0 20px rgba(0, 0, 0, 0.05);
                    }
                    .header {
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        padding: 30px 0;
                        background-color: #4f46e5;
                        border-radius: 10px 10px 0 0;
                        color: white;
                    }
                    .header img {
                        max-width: 40px;
                        margin-right: 10px;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 24px;
                    }
                    .content {
                        margin-top: 30px;
                        text-align: center;
                    }
                    .content h2 {
                        color: #4f46e5;
                        margin-bottom: 20px;
                    }
                    .content p {
                        color: #666666;
                        line-height: 1.6;
                    }
                    .otp {
                        font-size: 24px;
                        font-weight: bold;
                        margin: 20px 0;
                        background-color: rgba(255, 168, 0, 0.1);
                        padding: 15px;
                        border: 1px solid rgb(255, 168, 0);
                        border-radius: 10px;
                        display: inline-block;
                        color: rgb(255, 168, 0);
                    }
                    .footer {
                        margin-top: 40px;
                        text-align: center;
                        font-size: 12px;
                        color: #999999;
                    }
                    .footer a {
                        color: #4f46e5;
                        text-decoration: none;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                    <svg height="40px" width="40px" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" 
                        viewBox="0 0 503.118 503.118" xml:space="preserve">
                        <path style="fill:rgb(255, 168, 0);" d="M335.151,167.967c10.449,10.449,18.808,22.988,25.078,35.527
                            c22.988,48.065,15.673,108.669-25.078,148.375L223.347,464.718c-51.2,51.2-133.747,51.2-183.902,0
                            c-51.2-51.2-51.2-133.747,0-183.902l79.412-79.412c-9.404,31.347-8.359,64.784,3.135,95.086l-33.437,33.437
                            c-22.988,22.988-22.988,61.649,0,85.682c24.033,24.033,61.649,24.033,85.682,0l111.804-111.804
                            c11.494-11.494,17.763-27.167,17.763-42.841s-6.269-31.347-17.763-42.841c-11.494-11.494-27.167-17.763-42.841-17.763l56.424-56.424
                            C312.163,149.159,323.657,157.518,335.151,167.967z"/>
                        <path style="fill:rgb(255, 168, 0);" d="M167.967,335.151c-10.449-10.449-18.808-22.988-25.078-35.527
                            c-22.988-48.065-15.673-108.669,25.078-148.376L279.771,38.4c51.2-51.2,133.747-51.2,183.902,0c51.2,51.2,51.2,133.747,0,183.902
                            l-79.412,79.412c9.404-31.347,8.359-64.784-3.135-95.086l33.437-33.437c22.988-22.988,22.988-61.649,0-85.682
                            c-24.033-24.033-61.649-24.033-85.682,0L218.122,200.359c-11.494,11.494-17.763,27.167-17.763,42.841s6.269,31.347,17.763,42.841
                            c11.494,11.494,27.167,17.763,42.841,17.763l-56.424,56.424C190.955,353.959,179.461,345.6,167.967,335.151z"/>
                    </svg>
                        <h1>Shorty</h1>
                    </div>
                    <div class="content">
                        <h2>Welcome aboard!</h2>
                        <p>Dear $recipientName,</p>
                        <p>To complete your registration, please verify your email address using the One-Time Password (OTP) provided below. This helps us ensure the security of your account.</p>
                        <div class="otp">Your OTP Code: $otpCode</div>
                        <p>Please enter this code on the registration page to complete the verification process. This OTP is valid for [validity period, e.g., 10 minutes]. If it expires, you can request a new one by attempting to log in again.</p>
                        <p>If you did not initiate this registration, please disregard this email or contact our support team immediately.</p>
                        <p>Thank you for joining Shorty! We’re excited to have you on board.</p>
                        <p>Best regards,<br>
                           Shorty<br>
                          
                           <a href="https://www.ty.pavicontech.com/">[Company Website]</a>
                        </p>
                    </div>
                    <div class="footer">
                        <p>If you have any questions or need assistance, feel free to contact our support team at <a href="mailto:[support email]">[support email]</a> or visit our <a href="[help center/support page link]">help center</a>.</p>
                        <p>Note: This is an automated message. Please do not reply to this email.</p>
                        <p>[Your Company Name]<br>
                           info@pavicontech.com<br>
                           <a href="[Privacy Policy link]">Privacy Policy</a> | <a href="[Terms of Service link]">Terms of Service</a>
                        </p>
                    </div>
                </div>
            </body>
            </html>

        """.trimIndent()

    fun email2FATemplate(recipientName:String, otpCode:String) =
        """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Two-Factor Authentication (2FA) Verification</title>
                <style>
                    body {
                        font-family: 'Helvetica Neue', Arial, sans-serif;
                        background-color: #f4f4f4;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 30px auto;
                        background-color: #ffffff;
                        padding: 30px;
                        border-radius: 10px;
                        box-shadow: 0 0 20px rgba(0, 0, 0, 0.05);
                    }
                    .header {
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        padding: 30px 0;
                        background-color: #4f46e5;
                        border-radius: 10px 10px 0 0;
                        color: white;
                    }
                    .header img {
                        max-width: 40px;
                        margin-right: 10px;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 24px;
                    }
                    .content {
                        margin-top: 30px;
                        text-align: center;
                    }
                    .content h2 {
                        color: #4f46e5;
                        margin-bottom: 20px;
                    }
                    .content p {
                        color: #666666;
                        line-height: 1.6;
                    }
                    .otp {
                        font-size: 24px;
                        font-weight: bold;
                        margin: 20px 0;
                        background-color: rgba(255, 168, 0, 0.1);
                        padding: 15px;
                        border: 1px solid rgb(255, 168, 0);
                        border-radius: 10px;
                        display: inline-block;
                        color: rgb(255, 168, 0);
                    }
                    .footer {
                        margin-top: 40px;
                        text-align: center;
                        font-size: 12px;
                        color: #999999;
                    }
                    .footer a {
                        color: #4f46e5;
                        text-decoration: none;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <svg height="40px" width="40px" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" 
                            viewBox="0 0 503.118 503.118" xml:space="preserve">
                            <path style="fill:rgb(255, 168, 0);" d="M335.151,167.967c10.449,10.449,18.808,22.988,25.078,35.527
                                c22.988,48.065,15.673,108.669-25.078,148.375L223.347,464.718c-51.2,51.2-133.747,51.2-183.902,0
                                c-51.2-51.2-51.2-133.747,0-183.902l79.412-79.412c-9.404,31.347-8.359,64.784,3.135,95.086l-33.437,33.437
                                c-22.988,22.988-22.988,61.649,0,85.682c24.033,24.033,61.649,24.033,85.682,0l111.804-111.804
                                c11.494-11.494,17.763-27.167,17.763-42.841s-6.269-31.347-17.763-42.841c-11.494-11.494-27.167-17.763-42.841-17.763l56.424-56.424
                                C312.163,149.159,323.657,157.518,335.151,167.967z"/>
                            <path style="fill:rgb(255, 168, 0);" d="M167.967,335.151c-10.449-10.449-18.808-22.988-25.078-35.527
                                c-22.988-48.065-15.673-108.669,25.078-148.376L279.771,38.4c51.2-51.2,133.747-51.2,183.902,0c51.2,51.2,51.2,133.747,0,183.902
                                l-79.412,79.412c9.404-31.347,8.359-64.784-3.135-95.086l33.437-33.437c22.988-22.988,22.988-61.649,0-85.682
                                c-24.033-24.033-61.649-24.033-85.682,0L218.122,200.359c-11.494,11.494-17.763,27.167-17.763,42.841s6.269,31.347,17.763,42.841
                                c11.494,11.494,27.167,17.763,42.841,17.763l-56.424,56.424C190.955,353.959,179.461,345.6,167.967,335.151z"/>
                        </svg>
                        <h1>Shorty</h1>
                    </div>
                    <div class="content">
                        <h2>Two-Factor Authentication (2FA)</h2>
                        <p>Dear $recipientName,</p>
                        <p>To enhance the security of your account, we require you to verify your identity using the One-Time Password (OTP) sent to your registered mobile number.</p>
                        <div class="otp">Your OTP Code: $otpCode</div>
                        <p>Please enter this code on the 2FA verification page to complete the authentication process. This OTP is valid for [validity period, e.g., 10 minutes]. If it expires, you can request a new one by attempting to log in again.</p>
                        <p>If you did not initiate this verification, please disregard this email or contact our support team immediately.</p>
                        <p>Thank you for ensuring the security of your account.</p>
                        <p>Best regards,<br>
                           Shorty<br>
                           <a href="https://www.ty.pavicontech.com/">[Company Website]</a>
                        </p>
                    </div>
                    <div class="footer">
                        <p>If you have any questions or need assistance, feel free to contact our support team at <a href="mailto:[support email]">[support email]</a> or visit our <a href="[help center/support page link]">help center</a>.</p>
                        <p>Note: This is an automated message. Please do not reply to this email.</p>
                        <p>[Your Company Name]<br>
                           [Company Address]<br>
                           <a href="[Privacy Policy link]">Privacy Policy</a> | <a href="[Terms of Service link]">Terms of Service</a>
                        </p>
                    </div>
                </div>
            </body>
            </html>

        """.trimIndent()
}