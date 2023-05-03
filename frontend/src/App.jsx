import './App.css';
import { Outlet, RouterProvider, createBrowserRouter, useNavigation } from 'react-router-dom'
import Loading from './components/Loading';
import ErrorPage from './pages/Error';
import { Login, loginAction } from './pages/Login';
import { Register } from './pages/Register';
import Home from './pages/Home';
import { UserProvider } from './context/UserContext';
import { Dashboard } from './pages/Dashboard';
import { LoginRedirect } from './pages/LoginRedirect';

function Root() {
    const navigation = useNavigation();
    return (
        <>
            <Outlet />
            {navigation.state != 'idle' && <Loading />}
        </>
    )
}

const router = createBrowserRouter([
    {
        path: "/",
        element: <Root />,
        errorElement: <ErrorPage />,
        children: [
            {
                path: "/",
                element: <Home />,
            },
            {
                path: "/login",
                element: <Login />,
                action: loginAction
            },
            {
                path: "/register",
                element: <Register />
            },
            {
                path: "/login-redirect",
                element: <LoginRedirect />
            },
            {
                path: "dashboard",
                element: <Dashboard />
            },
            {
                path: "*",
                element: <h1>Not Found</h1>,
            }]
    }
]);

function App() {
    return (
        <UserProvider>
            <RouterProvider router={router} />
        </UserProvider>
    );
}

export default App
