import React from "react";
import styles from "./style.module.scss";
import LoginForm, { ILoginFormValues } from "../../components/forms/login-form";
import useAuthStore, { ILoginResponse } from "../../store/auth-store";
import { message } from "antd";

const LoginPage: React.FC = () => {
  // Access the signIn function from the authStore
  const signIn = useAuthStore((state) => state.signIn);

  const handleSubmit = async (values: ILoginFormValues) => {
    console.log("Received values of form: ", values);
    try {
      // Assuming your ILoginFormValues has email and password
      await signIn(values.email, values.password);
      console.log("Login successful");

      // Redirect to homepage or dashboard after successful login
      // This could be a React Router redirect or a window.location change, depending on your routing setup
      // Example: history.push('/dashboard');
    } catch (error) {
      console.error("Login failed", error);
      // Handle login failure, such as showing an error message
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
