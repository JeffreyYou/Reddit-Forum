export const formatDate = (isoString : string) => {
    const date = new Date(isoString);
    return date.toLocaleDateString(undefined, { day: 'numeric', month: 'long', year: 'numeric' });
};

// Dependency Injection
export const exceptionHandler = (fn: any) => {
    return async (...args : any[]) => {
        try {
            return await fn(...args)
        } catch (err) {
            console.error(err)
        }
    }
}
