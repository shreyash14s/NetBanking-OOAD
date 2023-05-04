import { useEffect, useState } from "react";

async function approveAction(row) {
    const url = `/api/admin/approveTask`;
    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
        body: JSON.stringify({ taskId: row.id }),
    });
    if (response.status == 200) {
        const data = await response.json();
        return data;
    } else {
        throw Error("Something went wrong");
    }
}

async function rejectAction(row) {
    const url = `/api/admin/rejectTask`;
    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
        body: JSON.stringify({ taskId: row.id }),
    });
    if (response.status == 200) {
        const data = await response.json();
        return data;
    } else {
        throw Error("Something went wrong");
    }
}

async function fetchAndSet(setData) {
    const url = `/api/admin/getTasks`;
    const response = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
    });
    if (response.status == 200) {
        const data = await response.json();
        setData(data);
    } else {
        throw Error("Something went wrong");
    }
}

function AdminTable({ data, setData }) {
    if (data.length == 0) {
        return (
            <tr>
                <td colSpan="2">No tasks to approve</td>
            </tr>
        );
    }
    return data.map((row) => (
            <tr key={row.id}>
                <td>{row.description}</td>
                <td>
                    <button
                        className="btn btn-primary btn-sm mr-2"
                        onClick={() => {
                            approveAction(row).then(() => { 
                                setData(null);
                                fetchAndSet(setData);
                            });
                        }}
                    >Approve</button>
                    <button
                        className="btn btn-danger btn-sm"
                        onClick={() => {
                            rejectAction(row).then(() => { 
                                setData(null);
                                fetchAndSet(setData);
                            });
                        }}
                    >Reject</button>
                </td>
            </tr>
        )
    );
}

export function Admin() {
    const [ data, setData ] = useState(null);

    useEffect(() => {
        fetchAndSet(setData);
    }, []);

    return (
        <div className="mb-72">
            <h1 className="mb-8">Admin Dashboard</h1>
            <table className="table">
                <thead>
                    <tr>
                        <th className="min-w-[500px]">Task</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {data == null ? (
                        <tr>
                            <td colSpan="2">Loading...</td>
                        </tr>
                    ) : <AdminTable data={data} setData={setData} />}
                </tbody>
            </table>
        </div>
    );
}