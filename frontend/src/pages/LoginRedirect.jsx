import { useContext, useEffect } from "react";
import { UserContext, getUserDetails } from "../context/UserContext";
import { useNavigate } from "react-router-dom";

async function fetchAndSet(token, setUser, navigate, controller) {
    try {
        const user = await getUserDetails(token, controller);
        setUser(user);
        console.log('User set and redirecting...', user);
        navigate('/dashboard');
    } catch (error) {
        console.error(error);
    }
}

export function LoginRedirect() {
    let { user, setUser } = useContext(UserContext);
    const navigate = useNavigate();
    useEffect(() => {
        let token = localStorage.getItem('token');
        let controller = new AbortController();
        fetchAndSet(token, setUser, navigate, controller);
        return () => {
            controller.abort();
        };
    }, [user, setUser, navigate]);
    return (
        <p>Loading...</p>
    );
}