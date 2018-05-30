'use strict';

describe('Controller Tests', function() {

    describe('HiringContact Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockHiringContact, MockCompany, MockHiringContactComment, MockHiringContactInternalComment, MockJobOrder, MockActivity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockHiringContact = jasmine.createSpy('MockHiringContact');
            MockCompany = jasmine.createSpy('MockCompany');
            MockHiringContactComment = jasmine.createSpy('MockHiringContactComment');
            MockHiringContactInternalComment = jasmine.createSpy('MockHiringContactInternalComment');
            MockJobOrder = jasmine.createSpy('MockJobOrder');
            MockActivity = jasmine.createSpy('MockActivity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'HiringContact': MockHiringContact,
                'Company': MockCompany,
                'HiringContactComment': MockHiringContactComment,
                'HiringContactInternalComment': MockHiringContactInternalComment,
                'JobOrder': MockJobOrder,
                'Activity': MockActivity
            };
            createController = function() {
                $injector.get('$controller')("HiringContactDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'recruitsmartApp:hiringContactUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
