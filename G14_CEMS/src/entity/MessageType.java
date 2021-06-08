package entity;

public enum MessageType {
	GetAllTests,
	UpdateTestDuration,
	TestsList,
	Error,
	SuccessUpdateTest,
	GetTestCount,
	TestCount,
	GetAllTestBanks,
	LockTest,
	SuccessLockTest,
	GetAllSubjects,
	RequestExtraTime,
	SentExtraTimeRequest,
	RefreshCourseTable,
	CourseList,
	AddCourse,
	CourseAdded,
	DeleteCourse,
	CourseDeleted,
	TestBanksList, QuestionBankList, addQuestion, insertQuestionBank,
	insertTestBank,


	logIn, getTestBankName, getCourseID, getTestID, TestList, CheckTest, CheckedTest, CheckStudentID, CheckedStudentID, CheckValidCode, CheckedCode, GetNextQID, GetSubjectID, getCoursesBySubject, GetQuestionsBySubject, GetCourseID, GetTCount, AddTest, Hello, GetQuestionByID, UpdateQuestion,

	 execCode, execCodeManual, downloadManualTest, submitManualTest, DeleteQuestion, GetNextTID, GetAllTestsBySubject, deleteTest, updateTest;


}
