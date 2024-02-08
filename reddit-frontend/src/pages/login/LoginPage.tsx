import React from "react";
import styles from "./style.module.scss";
import LoginForm, { ILoginFormValues } from "../../components/forms/login-form";
import useAuthStore, { ILoginResponse } from "../../store/auth-store";
import { message } from "antd";
import { useNavigate } from "react-router-dom";
import { useUserStore } from "../../store/user-store";
import {usePostStore} from "../../store/post-store";

const LoginPage: React.FC = () => {

  const { jwtToken,setJwtToken, fetchUserProfile, getDraftPosts, getHistoryPosts, getTop3Posts } = useUserStore();
  const {signIn} = useAuthStore();
  const navigate = useNavigate();
  const {setPostJwtToken} = usePostStore();
  const handleSubmit = async (values: ILoginFormValues) => {
    console.log("Received values of form: ", values);
    try {
      await signIn(values.email, values.password);
      // update user profile
      setJwtToken(jwtToken as string);
      setPostJwtToken(jwtToken as string);
      fetchUserProfile();
      getDraftPosts();
      getHistoryPosts();
      getTop3Posts();

      console.log("Login successful");
      navigate("/home");
    } catch (error: any) {
      console.error("Login failed", error);
      message.error(error.message);
    }
  };

  return (
    <div className={styles.login_container}>
      <h2>Log In</h2>
      <LoginForm onSubmit={handleSubmit} />
      <div className={styles.additionalLink}>
        New to RedditHub? <a href="/users/register">Sign Up</a>
      </div>
    </div>
  );
};

export default LoginPage;
