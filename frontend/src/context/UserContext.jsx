// User context
import { createContext, useState } from "react";

export const UserContext = createContext();

// eslint-disable-next-line react/prop-types
export function UserProvider({ children }) {
    const [user, setUser] = useState(null);
    
    return (
        <UserContext.Provider value={{ user, setUser }}>
            {children}
        </UserContext.Provider>
    );
}

export async function getUserDetails(token, controller) {
    const url = '/api/account/get';
    const response = await fetch(url, {
        signal: controller.signal,
        headers: {
            Authorization: `Bearer ${token}`,
        }
    });
    if (response.status != 200) {
        throw Error(response.statusText);
    }
    return await response.json();
}