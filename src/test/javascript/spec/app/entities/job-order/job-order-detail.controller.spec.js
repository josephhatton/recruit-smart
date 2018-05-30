'use strict';

describe('Controller Tests', function() {

    describe('JobOrder Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockJobOrder, MockJobOrderComment, MockAddress, MockSkill, MockCompany, MockJobOrderInternalComment, MockHiringContact, MockActivity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockJobOrder = jasmine.createSpy('MockJobOrder');
            MockJobOrderComment = jasmine.createSpy('MockJobOrderComment');
            MockAddress = jasmine.createSpy('MockAddress');
            MockSkill = jasmine.createSpy('MockSkill');
            MockCompany = jasmine.createSpy('MockCompany');
            MockJobOrderInternalComment = jasmine.createSpy('MockJobOrderInternalComment');
            MockHiringContact = jasmine.createSpy('MockHiringContact');
            MockActivity = jasmine.createSpy('MockActivity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'JobOrder': MockJobOrder,
                'JobOrderComment': MockJobOrderComment,
                'Address': MockAddress,
                'Skill': MockSkill,
                'Company': MockCompany,
                'JobOrderInternalComment': MockJobOrderInternalComment,
                'HiringContact': MockHiringContact,
                'Activity': MockActivity
            };
            createController = function() {
                $injector.get('$controller')("JobOrderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'recruitsmartApp:jobOrderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
