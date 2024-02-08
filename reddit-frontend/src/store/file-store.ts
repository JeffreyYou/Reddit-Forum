import { create } from "zustand";

interface IFileStore {
    jwtToken: string
    imageUrls: string[]
    attachmentUrls: string[]
    putImageUrl: (url: string) => Promise<void>
    deleteImageUrl: (url: string) => Promise<void>;
    // putAttachmentUrl: (url: string) => Promise<void>
    // deleteAttachmentUrl: (url: string) => Promise<void>;
}

export const useObjectStore = create<IFileStore>((set, get) => ({
    jwtToken: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XX0.uiM5Llbx-FYb4rbwV33BR04lmpJpDP6Sq-uD73DWSxw",
    imageUrls: [],
    attachmentUrls: [],
    putImageUrl: async (url: string) => {
        const oldList: string[] = get().imageUrls
        set({imageUrls: [...oldList, url]});
    },
    deleteImageUrl: async (url: string) => {
        const oldList: string[] = get().imageUrls;
        const updatedList = oldList.filter(imageUrl => imageUrl !== url);
        set({ imageUrls: updatedList });
    },

}));