import { create } from "zustand";
import { persist } from "zustand/middleware";

interface IThemeProps {
    theme: string;
}
interface IThemeStore {
    themeProps: IThemeProps;
    setTheme: (newTheme: string) => void;
}


export const useThemeStore = create<IThemeStore>()(
    persist(
        (set, get) => ({
            themeProps: {
                theme: 'light',
            },
            setTheme: (newTheme) => {
                set({ themeProps: { theme: newTheme } })
            },
        }),
        {
            name: 'theme',
        }
    )
);

