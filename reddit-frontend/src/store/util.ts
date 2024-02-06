export const formatDate = (isoString : string) => {
    const date = new Date(isoString);
    return date.toLocaleDateString(undefined, { day: 'numeric', month: 'long', year: 'numeric' });
};