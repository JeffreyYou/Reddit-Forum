import { create } from "zustand";
import { persist } from "zustand/middleware";

interface IUserStore {
    firstName: string;
    lastName: string;
    registrationDate: string;
    top3Posts: string[];
    draftsList: string[];
    setUser: () => void;
    getUser: () => IUserInfo;
}
interface IUserInfo {
    firstName: string;
    lastName: string;
    registrationDate: string;
    top3Posts: string[];
    draftsList: string[];
}

export const useUserStore = create<IUserStore>()(
    persist(
        (set, get) => ({
            firstName: "Jeffrey",
            lastName: "You",
            registrationDate: "2024-01-01T00:00:00.000Z",
            top3Posts: [],
            draftsList: [],
            setUser: () => {
                set({ firstName: "Jeffrey", lastName: "You", registrationDate: "2024-01-01T00:00:00.000Z", top3Posts: [], draftsList: [] })
            },
            
            getUser: () => {
                return {
                    firstName: get().firstName,
                    lastName: "You",
                    registrationDate: "2024-01-01T00:00:00.000Z",
                    top3Posts: [],
                    draftsList: [],
                } as IUserInfo;
            }
        }),
        {
            name: "user_store",
        }
    )
);






