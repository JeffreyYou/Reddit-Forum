import React from "react";
import { useNavigate } from "react-router-dom";
import styles from "./style.module.scss";
import RegisterForm from "../../components/forms/register-form";
import useAuthStore from "../../store/auth-store";
import { message } from "antd";
import { IRegisterFormValues } from "../../store/interface";

const RegisterPage: React.FC = () => {
  const navigate = useNavigate();
  const { signUp } = useAuthStore();

  const onFinish = async (values: IRegisterFormValues) => {
    try {
      // Destructuring to match the signUp function parameters
      const { email, password, firstName, lastName } = values;
      await signUp(email, password, firstName, lastName);
      message.success("Registration successful! Please log in.");
      navigate("/users/login");
    } catch (error) {
      console.error("Registration failed: ", error);
      message.error("Email already exists. Please try again.");
    }
  };

  return (
    <div className={styles.register_container}>
      <h2>Sign Up</h2>
      <RegisterForm onFinish={onFinish} />
      <div className={styles.additionalLink}>
        Already have an account? <a href="/users/login">Login</a>
      </div>
    </div>
  );
};

export default RegisterPage;
