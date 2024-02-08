import { create } from "zustand";

interface IFileStore {
    jwtToken: string
    imageUrls: string[]
    attachmentUrls: string[]
    putImageUrl: (url: string) => Promise<void>
    deleteImageUrl: (url: string) => Promise<void>;
    putAttachmentUrl: (url: string) => Promise<void>
    deleteAttachmentUrl: (url: string) => Promise<void>;
}

export const useObjectStore = create<IFileStore>((set, get) => ({
    jwtToken: "",
    //jwtToken: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMiIsInBlcm1pc3Npb25zIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV19.SOuhK8B4kr9qsmAqMheWGLgUFpyumlNX8BKDPi3U_jE",
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
    putAttachmentUrl: async (url: string) => {
        const oldList: string[] = get().attachmentUrls
        set({attachmentUrls: [...oldList, url]});
    },
    deleteAttachmentUrl: async (url: string) => {
        const oldList: string[] = get().attachmentUrls;
        const updatedList = oldList.filter(attachmentUrls => attachmentUrls !== url);
        set({ attachmentUrls: updatedList });
    },

}));