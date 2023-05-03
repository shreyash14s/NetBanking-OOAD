import { useContext } from "react";
import { UserContext } from "../context/UserContext";

// export async function userLoader({ request, params }) {
//   // const taskId = params.taskId;
//     const token = localStorage.getItem('token');
//     const task = await getUserDetails(token);
//     return { task };
// }

export function Dashboard() {
    let { user } = useContext(UserContext);

    return (
        <>
            <h1>Dashboard</h1>
            <p>Hi {user?.userName}</p>
        </>
    );
}
