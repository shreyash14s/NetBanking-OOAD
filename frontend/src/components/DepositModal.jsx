import { useState } from "react";
import { Form } from "react-router-dom";

export default function DepositModal({ open, setOpen }) {
    const [ amount, setAmount ] = useState(0);
    const [ loading, setLoading ] = useState(false);

    const doDeposit = async () => {
        setLoading(true);
        try {
            const url = '/api/transactions/create';
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    toAccountNumber: '',
                    transactionAmount: amount,
                    transactionType: 'deposit',
                }),
            });
            if (response.status != 200) {
                throw Error('Failed to deposit');
            }
            const res = await response.json();
            console.log(res)
            setOpen(false);
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <input type="checkbox" id="deposit-modal" className="modal-toggle" checked={open} readOnly />
            <div className="modal">
                <div className="modal-box">
                    <h3 className="font-bold text-lg">Deposit!</h3>
                    <p className="py-4">Enter the amount to deposit:</p>
                    <div className="flex items-center justify-center">
                        <div className="form-control max-w-xs">
                            <label className="label">
                                <span className="label-text">Deposit amount</span>
                            </label>
                            <input
                                type="number"
                                placeholder="Type here"
                                className={"input input-bordered max-w-xs" + (loading ? ' input-disabled' : '')}
                                name="amount"
                                value={amount}
                                onChange={(e) => setAmount(e.target.value)}
                            />
                        </div>
                    </div>
                    <div className="modal-action">
                        {/* <label htmlFor="deposit-modal" className="btn">Ok</label> */}
                        <button className="btn" onClick={doDeposit}>Ok</button>
                    </div>
                </div>
            </div>
        </>
    )
}