import { create } from "zustand";
import { persist } from "zustand/middleware";

interface AuthState {
  isLoggedIn: boolean;
  jwtToken: string | null;
  email: string | null;
  firstname: string | null;
  lastname: string | null;
  signIn: (email: string, password: string) => Promise<void>;
  signOut: () => void;
}

const useAuthStore = create<AuthState>()(
  persist(
    (set, get) => ({
      // Initial state
      isLoggedIn: false,
      jwtToken: null,
      email: null,
      firstname: null,
      lastname: null,

      // Sign in function
      signIn: async (email, password) => {
        try {
          // Replace this URL with your actual login API endpoint
          const loginUrl = "http://localhost:8081/auth-service/user/login";
          const response = await fetch(loginUrl, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ email, password }),
          });

          if (!response.ok) {
            throw new Error("Login failed");
          }

          // TODO fetch user first name and last name

          const data: ILoginResponse = await response.json();
          localStorage.setItem("jwtToken", data.token)

          set({
            isLoggedIn: true,
            jwtToken: data.token,
            email: email,
          });
        } catch (error) {
          console.error("Failed to sign in", error);
          throw error;
        }
      },

      // Sign out function
      signOut: () => {
        set({ isLoggedIn: false, jwtToken: null, email: null });
      },
    }),
    {
      name: "auth_storage", // Unique name for localStorage key
    }
  )
);

export interface ILoginResponse {
  message: string;
  token: string;
}

export interface ILoginRequest {
  email: string;
  password: string;
}

export default useAuthStore;
