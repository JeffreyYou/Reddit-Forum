import { create } from "zustand";
import { persist } from "zustand/middleware";
import { IUserProfile } from "./interface";
import { formatDate } from "./util";

interface IUserStore {
    user: IUserProfile;
    jwtToken: string;
    fetchUserProfile: () => Promise<IUserProfile>;
    updateUserProfile: (user: IUserProfile) => void;
}
const domain = "http://localhost:8081"
const url = `${domain}/user-service/user/profile`;

export const useUserStore = create<IUserStore>()(
    persist(
        (set, get) => ({
            user: {
                firstName: "Jeffrey",
                lastName: "You",
                email: "yqse521749@gmail.com",
                active: true,
                dateJoined: formatDate("2024-01-01T00:00:00.000Z"),
                type: "user",
                profileImageURL: "https://api.dicebear.com/7.x/miniavs/svg?seed=8",
                verified: true,
            },
            // admin user, id = 1, test only
            jwtToken: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XX0.J2_B1Y8STCtF_8oQF0gndAklds6dezvR6SJocK-sB9g",
            
            fetchUserProfile: async (): Promise<IUserProfile> => {
                const jwt = get().jwtToken;
                try {
                    const response = await fetch(url, {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${jwt}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to fetch user profile');
                    }
                    // format date
                    const data: IUserProfile = await response.json();
                    data.dateJoined = formatDate(data.dateJoined);
                    // update user profile in the store
                    set({ user: data });
                    return data;
                } catch (error) {
                    throw new Error('Failed to fetch user profile');
                }
            },
            updateUserProfile: (user: IUserProfile) => {
                set({ user: user });
            }
        }),
        {
            name: "user_profile",
        }
    )
);






